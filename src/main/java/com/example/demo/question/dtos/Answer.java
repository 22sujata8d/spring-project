package com.example.demo.question.dtos;

import java.time.LocalDate;

public class Answer {
    private final Long id;
    private final String fullName;
    private final String answer;
    private final LocalDate dateAdded;

    public Answer(Long id, String fullName, String answer, LocalDate dateAdded) {
        this.id = id;
        this.fullName = fullName;
        this.answer = answer;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }
}
