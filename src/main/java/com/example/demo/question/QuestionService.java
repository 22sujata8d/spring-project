package com.example.demo.question;

import com.example.demo.answer.AnswerRepository;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.question.dtos.Answer;
import com.example.demo.question.dtos.QuestionAnswerDto;
import com.example.demo.question.dtos.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public QuestionAnswerDto getQuestionAnswersByQuestionId(Long questionId) {
        Boolean questionExists = questionRepository.selectQuestionExists(questionId);
        if (!questionExists) {
            throw new NotFoundException(String.format("question with id %d not found", questionId));
        }

        Optional<com.example.demo.question.Question> questionOptional = questionRepository.findById(questionId);
        Question question = convertQuestionEntityToDTO(questionOptional.get());
        List<com.example.demo.answer.Answer> answers = answerRepository.findAnswersByQuestionId(questionId);
        List<Answer> answersDto = new ArrayList<>();

        for (com.example.demo.answer.Answer ans : answers) {
            answersDto.add(convertAnswerEntityToDTO(ans));
        }

        return new QuestionAnswerDto(
                question,
                answersDto
        );
    }

    public List<QuestionAnswerDto> getAllQuestionsAnswers() {
        List<com.example.demo.question.Question> questions = questionRepository.findAll();
        List<QuestionAnswerDto> allQuestions = new ArrayList<>();

        for (com.example.demo.question.Question ques : questions) {
            List<com.example.demo.answer.Answer> answers = answerRepository.findAnswersByQuestionId(ques.getId());

            Question questionDto = convertQuestionEntityToDTO(ques);
            List<Answer> answersDto = new ArrayList<>();
            for (com.example.demo.answer.Answer ans : answers) {
                answersDto.add(convertAnswerEntityToDTO(ans));
            }

            allQuestions.add(new QuestionAnswerDto(questionDto, answersDto));
        }

        return allQuestions;
    }

    public Question addNewQuestion(com.example.demo.question.Question question) {
        if (!isValidQuestionBody(question)) {
            throw new BadRequestException("full name or question should not be empty");
        }

        question.setDateAdded(LocalDate.now());
        com.example.demo.question.Question newQuestion = questionRepository.save(question);

        return convertQuestionEntityToDTO(newQuestion);
    }

    private boolean isValidQuestionBody(com.example.demo.question.Question question) {
        if (question.getQuestion() == null || question.getFullName() == null) {
            return false;
        }
        return !question.getQuestion().isEmpty() && !question.getFullName().isEmpty();
    }

    private Answer convertAnswerEntityToDTO(com.example.demo.answer.Answer answer) {

        return new Answer(
                answer.getId(),
                answer.getFullName(),
                answer.getAnswer(),
                answer.getDateAdded()
        );
    }

    private Question convertQuestionEntityToDTO(com.example.demo.question.Question question) {

        return new Question(
                question.getId(),
                question.getFullName(),
                question.getQuestion(),
                question.getDateAdded());
    }
}
