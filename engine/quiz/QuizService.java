package engine.quiz;

import engine.exception.QuizNotFoundException;
import engine.exception.UnauthorizedAccessException;
import engine.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class QuizService {
    private QuizResult correctAns, wrongAns;

    @Autowired private QuizRepository quizRepository;
    @Autowired private UserRepository userRepository;

    QuizService () {
        correctAns = new QuizResult(true, "Congratulations, you're right!");
        wrongAns = new QuizResult(false, "Wrong answer! Please, try again.");
    }

    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
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

    public QuizResult evaluateQuiz(int id, QuizAnswer answer) {
        int []originalAnswer = getQuiz(id).getAnswer();
        int []providedAnswer= answer.getAnswer();

        if (originalAnswer.length != providedAnswer.length) return wrongAns;
        for (int i = 0; i < originalAnswer.length; i++) {
            if (originalAnswer[i] != providedAnswer[i]) return wrongAns;
        }
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
}
