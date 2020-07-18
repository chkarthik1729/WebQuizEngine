package engine.quiz;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class QuizAnswer {

    int []answer;

    private QuizAnswer() {}

    @JsonCreator
    public static QuizAnswer getInstance(@JsonProperty("answer") int[] answer) {
        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.answer = answer != null ? answer : new int[0];
        return quizAnswer;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
