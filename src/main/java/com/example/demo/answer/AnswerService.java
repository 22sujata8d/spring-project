package com.example.demo.answer;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public com.example.demo.question.dtos.Answer addAnswerByQuestionId(Answer answer, Long questionId) {
            if (!isValidAnswerBody(answer)) {
                throw new BadRequestException("full name or answer should not be empty");
            }
            Boolean questionExists = questionRepository.selectQuestionExists(questionId);
            if (!questionExists) {
                throw new NotFoundException(String.format("question with question id %d not found", questionId));
            }

            answer.setDateAdded(LocalDate.now());
            answer.setQuestionId(questionId);

            Answer answerEntity = answerRepository.save(answer);
            return convertAnswerEntityToAnswerDto(answerEntity);
    }

    private boolean isValidAnswerBody(Answer answer) {
        if (answer.getAnswer() == null || answer.getFullName() == null) {
            return false;
        }
        return !answer.getFullName().isEmpty() && !answer.getAnswer().isEmpty();
    }

    private com.example.demo.question.dtos.Answer convertAnswerEntityToAnswerDto(Answer answer) {
        return new com.example.demo.question.dtos.Answer(
                answer.getId(),
                answer.getFullName(),
                answer.getAnswer(),
                answer.getDateAdded()
        );
    }
}
