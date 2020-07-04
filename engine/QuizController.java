package engine;

import engine.quiz.Quiz;
import engine.quiz.QuizAnswer;
import engine.quiz.QuizResult;
import engine.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    QuizService service;

    @GetMapping
    public List<Quiz> getQuizzes() {
        return service.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable @Min(1) int id) {
        try {
            return service.getQuiz(id);
        } catch (IndexOutOfBoundsException ignored) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Required quiz not available");
        }
    }

    @PostMapping
    public Quiz addQuiz(@RequestBody @Valid @NotNull Quiz quiz) {
        service.addQuiz(quiz);
        return quiz;
    }

    @PostMapping("/{id}/solve")
    public QuizResult submitQuiz(@PathVariable @Min(1) int id,
                                 @RequestBody @NotNull QuizAnswer answer) {
        return service.evaluateQuiz(id, answer);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
