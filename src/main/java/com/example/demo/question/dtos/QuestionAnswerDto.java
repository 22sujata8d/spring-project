package com.example.demo.question.dtos;

import java.util.List;

public class QuestionAnswerDto {
    private final Question question;
    private final List<Answer> answers;

    public QuestionAnswerDto(Question question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
