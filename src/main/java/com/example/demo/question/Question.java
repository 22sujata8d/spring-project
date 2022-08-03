package com.example.demo.question;

import javax.persistence.*;
import java.time.LocalDate;

import static org.hibernate.id.SequenceGenerator.SEQUENCE;

@Entity(name = "Question")
@Table(
        name = "question",
        uniqueConstraints = @UniqueConstraint(
                name = "question_unique", columnNames = {"question"}
        )
)
public class Question {
    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    private Long id;

    //@Column(name = "question", columnDefinition = "TEXT", nullable = false)
    private String question;

    //@Column(name = "full_name", columnDefinition = "TEXT", nullable = false)
    private String fullName;

    //@Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    public Question(String question, String fullName) {
        this.question = question;
        this.fullName = fullName;
    }

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
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
}

