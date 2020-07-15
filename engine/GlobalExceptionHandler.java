package engine;

import engine.exception.QuizNotFoundException;
import engine.exception.UnauthorizedAccessException;
import engine.exception.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleUnauthorizedAccessException(Exception exception) {
    }

    @ExceptionHandler({UsernameAlreadyTakenException.class,
                        ConstraintViolationException.class,
                        MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequests(Exception exception) {
        return exception.getMessage();
    }
}
