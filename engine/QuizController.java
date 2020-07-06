package engine;

import engine.quiz.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@Validated
@RestController
@RequestMapping(value = "/api/quizzes", produces = "application/json")
public class QuizController {

    @Autowired
    QuizService service;

    @GetMapping
    public List<Quiz> getQuizzes() {
        return service.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable @Min(1) int id) {
        return service.getQuiz(id);
    }

    @PostMapping
    @ResponseBody
    public Quiz addQuiz(@RequestBody @Valid @NotNull Quiz quiz) {
        return service.addQuiz(quiz);
    }

    @PostMapping("/{id}/solve")
    public QuizResult submitQuiz(@PathVariable @Min(1) int id,
                                 @RequestBody @NotNull QuizAnswer answer) {
        return service.evaluateQuiz(id, answer);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleConstraintViolationException() {
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException() {
    }
}
