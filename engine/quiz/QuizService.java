package engine.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class QuizService {
    private List<Quiz> quizzes;
    private AtomicInteger runningId;
    private QuizResult correctAns, wrongAns;

    QuizService () {
        quizzes = new ArrayList<>();
        runningId = new AtomicInteger(1);
        correctAns = new QuizResult(true, "Congratulations, you're right!");
        wrongAns = new QuizResult(false, "Wrong answer! Please, try again.");
    }

    public List<Quiz> getAllQuizzes() {
        return quizzes;
    }

    public Quiz getQuiz(int id) {
        try {
            return quizzes.get(id-1);
        } catch (IndexOutOfBoundsException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested quiz not found");
        }
    }

    public Quiz addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        quiz.setId(runningId.getAndIncrement());
        return quiz;
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
