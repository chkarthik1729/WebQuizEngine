package engine;

import engine.quiz.*;

import engine.user.User;
import engine.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class Controller {

    @Autowired QuizService quizService;
    @Autowired UserService userService;

    @GetMapping("/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuiz(@PathVariable @Min(1) int id) {
        return quizService.getQuiz(id);
    }

    @ResponseBody
    @PostMapping("/quizzes")
    public Quiz addQuiz(@RequestBody @Valid @NotNull Quiz quiz, Authentication authentication) {
        return quizService.addQuiz(quiz, authentication);
    }

    @PostMapping("/quizzes/{id}/solve")
    public QuizResult submitQuiz(@PathVariable @Min(1) int id,
                                 @RequestBody @NotNull QuizAnswer answer) {
        return quizService.evaluateQuiz(id, answer);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid User user) {
        userService.registerUser(user);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable @Min(1) int id, Authentication authentication) {
        return quizService.deleteQuiz(id, authentication);
    }
}
