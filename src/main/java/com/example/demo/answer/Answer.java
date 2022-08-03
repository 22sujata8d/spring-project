package com.example.demo.answer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Answer")
@Table(name = "answer")
public class Answer {
    @Id
    @SequenceGenerator(
            name = "answer_sequence",
            sequenceName = "answer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "answer_sequence"
    )
    private Long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    public Answer(String fullName, String answer) {
        this.answer = answer;
        this.fullName = fullName;
    }

    public Answer() {
    }

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", answer='" + answer + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
