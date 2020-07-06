package engine.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class QuizService {
    private QuizResult correctAns, wrongAns;

    @Autowired
    private QuizRepository quizRepository;

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
            throw new NoSuchElementException();
        }
        return optionalQuiz.get();
    }

    public Quiz addQuiz(Quiz quiz) {
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
}
