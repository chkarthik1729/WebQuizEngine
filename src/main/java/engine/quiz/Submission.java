package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Submission {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int submissionId;

    @JsonIgnore
    String username;

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    int quizId;

    @JsonProperty(value = "completedAt", access = JsonProperty.Access.READ_ONLY)
    Timestamp timestamp;

    public Submission() {
    }

    public Submission(String username, int quizId, long timestamp) {
        this.username = username;
        this.quizId = quizId;
        this.timestamp = new Timestamp(timestamp);
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
