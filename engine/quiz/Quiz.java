package engine.quiz;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonPropertyOrder({"id", "title", "text", "options"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Quiz {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotNull
    @Size(min = 2)
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    public Quiz() {
    }

    @JsonCreator
    public static Quiz getInstance(@JsonProperty("title") String title,
                            @JsonProperty("text") String text,
                            @JsonProperty("options") String[] options,
                            @JsonProperty("answer") int[] answer) {
        Quiz quiz = new Quiz();
        quiz.title = title;
        quiz.text = text;
        quiz.options = options;
        quiz.answer = answer != null ? answer : new int[0];
        return quiz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
