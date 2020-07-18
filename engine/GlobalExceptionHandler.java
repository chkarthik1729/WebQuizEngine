package engine;

import engine.exception.QuizNotFoundException;
import engine.exception.UnauthorizedAccessException;
import engine.exception.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(QuizNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder("");

        for (ConstraintViolation csv: e.getConstraintViolations()) {
            message.append(csv.getMessageTemplate())
                    .append("\n");
        }

        return message.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder("");

        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            message.append(objectError.getDefaultMessage())
                    .append("\n");
        }

        return message.toString();
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUsernameAlreadyTakenException(UsernameAlreadyTakenException e) {
        return e.getMessage();
    }
}
