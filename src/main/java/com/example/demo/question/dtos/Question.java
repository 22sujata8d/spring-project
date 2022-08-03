package com.example.demo.question.dtos;

import java.time.LocalDate;

public class Question {
    private final Long id;
    private final String fullName;
    private final String question;
    private final LocalDate dateAdded;

    public Question(Long id, String fullName, String question, LocalDate dateAdded) {
        this.id = id;
        this.fullName = fullName;
        this.question = question;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getQuestion() {
        return question;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }
}
