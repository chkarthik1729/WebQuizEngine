package engine;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QuizService {
    private List<Quiz> quizzes;
    private AtomicInteger runningId;
    private QuizResult correctAns, wrongAns;
    Matcher answerMatcher;

    QuizService () {
        quizzes = new ArrayList<>();
        runningId = new AtomicInteger(1);
        correctAns = new QuizResult(true, "Congratulations, you're right!");
        wrongAns = new QuizResult(false, "Wrong answer! Please, try again.");
        answerMatcher = Pattern.compile("\\s*answer\\s*=\\s*(\\d+)\\s*", Pattern.CASE_INSENSITIVE)
                                .matcher("");
    }

    public List<Quiz> getAllQuizzes() {
        return quizzes;
    }

    public Quiz getQuiz(int id) {
        try {
            return quizzes.get(id-1);
        } catch (IndexOutOfBoundsException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
    }

    public Quiz addQuiz(Quiz quiz) {
        quiz.setId(runningId.getAndIncrement());
        quizzes.add(quiz);
        return quiz;
    }

    public QuizResult evaluateQuiz(int id, String body) {
        answerMatcher.reset(body);
        if (answerMatcher.matches()) {
            int submittedAnswer = Integer.parseInt(answerMatcher.group(1));
            if (submittedAnswer == getQuiz(id).getAnswer()) {
                return correctAns;
            }
            return wrongAns;
        } else {
            return null;
        }
    }
}
