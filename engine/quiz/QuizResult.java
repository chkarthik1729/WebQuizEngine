package engine.quiz;

public class QuizResult {
    private boolean success;
    private String feedback;

    public QuizResult() {
    }

    public QuizResult(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
