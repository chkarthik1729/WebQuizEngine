package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    QuizService service;

    @GetMapping
    public List<Quiz> getQuiz() {
        return service.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        try {
            return service.getQuiz(id);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Required quiz not available");
        }
    }

    @PostMapping
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return service.addQuiz(quiz);
    }

    @PostMapping("/{id}/solve")
    public QuizResult submitQuiz(@PathVariable int id, @RequestBody String body) {
        return service.evaluateQuiz(id, body);
    }
}
