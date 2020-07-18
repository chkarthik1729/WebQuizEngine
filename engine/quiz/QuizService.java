package engine.quiz;

import engine.exception.QuizNotFoundException;
import engine.exception.UnauthorizedAccessException;
import engine.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuizService {
    private final static QuizResult
            correctAns = new QuizResult(true, "Congratulations, you're right!"),
            wrongAns = new QuizResult(false, "Wrong answer! Please, try again.");

    @Autowired private QuizRepository quizRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SubmissionRepository submissionRepository;

    QuizService () {
    }

    public Page<Quiz> getPagedQuizzes(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return quizRepository.findAll(pageable);
    }

    public Page<Submission> getPagedSubmissions(int page, int pageSize, Authentication authentication) {
        String username = authentication.getName();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
        return submissionRepository.getSubmissions(username, pageable);
    }

    public Quiz getQuiz(int id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (!optionalQuiz.isPresent()) {
            throw new QuizNotFoundException(id);
        }
        return optionalQuiz.get();
    }

    public Quiz addQuiz(Quiz quiz, Authentication authentication) {
        String username = authentication.getName();
        quiz.setCreator(username);
        return quizRepository.save(quiz);
    }

    public QuizResult evaluateQuiz(int id, QuizAnswer answer, Authentication authentication) {
        int []originalAnswer = getQuiz(id).getAnswer();
        int []providedAnswer= answer.getAnswer();

        if (originalAnswer.length != providedAnswer.length) return wrongAns;
        for (int i = 0; i < originalAnswer.length; i++) {
            if (originalAnswer[i] != providedAnswer[i]) return wrongAns;
        }

        Submission submission = new Submission(authentication.getName(), id, System.currentTimeMillis());
        submissionRepository.save(submission);
        return correctAns;
    }

    public ResponseEntity deleteQuiz(int id, Authentication authentication) {
        Quiz quiz = getQuiz(id);
        if (!quiz.getCreator().equals(authentication.getName())) {
            throw new UnauthorizedAccessException();
        }
        quizRepository.delete(quiz);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity updateQuiz(int id, Quiz quiz, Authentication authentication) {
        if (quizRepository.existsById(id)) {
            if (!getQuiz(id).getCreator().equals(authentication.getName())) {
                throw new UnauthorizedAccessException();
            }

            quiz.setId(id);
            return new ResponseEntity(addQuiz(quiz, authentication), HttpStatus.OK);

        } else {
            return new ResponseEntity(addQuiz(quiz, authentication), HttpStatus.CREATED);
        }
    }
}
