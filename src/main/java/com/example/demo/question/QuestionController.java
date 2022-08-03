package com.example.demo.question;

import com.example.demo.question.dtos.QuestionAnswerDto;
import com.example.demo.question.dtos.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<QuestionAnswerDto> getAllQuestions() {

        return questionService.getAllQuestionsAnswers();
    }

    @GetMapping("/{questionId}")
    public QuestionAnswerDto getQuestionAnswersByQuestionId(@PathVariable Long questionId) {
        return questionService.getQuestionAnswersByQuestionId(questionId);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Question addQuestion(@RequestBody com.example.demo.question.Question question) {

        return questionService.addNewQuestion(question);
    }
}
