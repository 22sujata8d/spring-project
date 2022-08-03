package com.example.demo.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping(
            path =  "/{questionId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public com.example.demo.question.dtos.Answer addAnswerByQuestionId(@RequestBody Answer answer, @PathVariable Long questionId) {
        return answerService.addAnswerByQuestionId(answer, questionId);
    }
}
