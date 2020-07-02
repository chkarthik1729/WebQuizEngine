package engine;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quiz {
    private int id;
    private String title;
    private String text;
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int answer;

    public Quiz() {
    }

    public Quiz(String title, String text, String[] options) {
        this.title = title;
        this.text = text;
        this.options = options;
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

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
