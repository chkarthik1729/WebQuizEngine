package engine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    Quiz quiz;
    QuizResult result;

    public QuizController() {
        quiz = new Quiz();
        result = new QuizResult();
        quiz.setTitle("The Java Logo");
        quiz.setText("What is depicted on the Java logo?");
        quiz.setOptions(new String[] {"Robot","Tea leaf","Cup of coffee","Bug"});
    }

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return quiz;
    }

    @PostMapping("/api/quiz")
    public QuizResult evaluateQuiz(@RequestBody String answer) {
        if ("answer=2".equals(answer)) {
            result.setSuccess(true);
            result.setFeedback("Congratulations, you're right!");
            return result;
        }
        result.setSuccess(false);
        result.setFeedback("Wrong answer! Please, try again.");
        return result;
    }
}
