package engine.exception;

import org.apache.logging.log4j.message.StringFormattedMessage;

public class QuizNotFoundException extends RuntimeException {

    private static final String errorMsg = "Requested quiz with id: %d is not present";

    public QuizNotFoundException(int quizId) {
        super(new StringFormattedMessage(errorMsg, quizId).toString());
    }
}
