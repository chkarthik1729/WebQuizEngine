package engine;

import engine.quiz.*;

import engine.user.User;
import engine.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated()
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class Controller {

    @Autowired QuizService quizService;
    @Autowired UserService userService;

    @GetMapping("/quizzes")
    public Page<Quiz> getPagedQuizzes(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                      @RequestParam(required = false, defaultValue = "10") @Min(1) int pageSize) {
        return quizService.getPagedQuizzes(page, pageSize);
    }

    @GetMapping("/quizzes/completed")
    public Page<Submission> getCompletedQuizzes(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                @RequestParam(required = false, defaultValue = "10") @Min(1) int pageSize,
                                                Authentication authentication) {
        return quizService.getPagedSubmissions(page, pageSize, authentication);
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuiz(@PathVariable @Min(value = 1, message = "Id must be a positive integer") int id) {
        return quizService.getQuiz(id);
    }

    @ResponseBody
    @PostMapping("/quizzes")
    public Quiz addQuiz(@RequestBody @Valid @NotNull Quiz quiz, Authentication authentication) {
        return quizService.addQuiz(quiz, authentication);
    }

    @PostMapping("/quizzes/{id}/solve")
    public QuizResult submitQuiz(@PathVariable @Min(value = 1, message = "Id must be a positive integer") int id,
                                 @RequestBody @NotNull QuizAnswer answer,
                                 Authentication authentication) {
        return quizService.evaluateQuiz(id, answer, authentication);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid User user) {
        userService.registerUser(user);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable @Min(value = 1, message = "Id must be a positive integer") int id,
                                     Authentication authentication) {
        return quizService.deleteQuiz(id, authentication);
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity updateQuiz(@PathVariable @Min(value = 1, message = "Id must be a positive integer") int id,
                                     @RequestBody @Valid @NotNull Quiz quiz, Authentication authentication) {
        return quizService.updateQuiz(id, quiz, authentication);
    }
}
