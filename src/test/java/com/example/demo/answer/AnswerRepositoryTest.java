package com.example.demo.answer;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void getAnswerByQuestionIdWhenAnswerExists() {
        // given
       Long questionId = 1L;
       Answer answer = new Answer(
         "Full name",
         "this is the life..."
       );

       answer.setDateAdded(LocalDate.now());
       answer.setQuestionId(questionId);

       List<Answer> expectedAnswers = List.of(answer);

       underTest.save(answer);

        // when
        List<Answer> answers = underTest.findAnswersByQuestionId(questionId);

        // then
        assertThat(answers).usingRecursiveComparison().isEqualTo(expectedAnswers);
    }

    @Test
    void getAnswersByQuestionIdWhenAnswerDoesNotExists() {
        // given
        Long questionId = 1L;

        List<Answer> expectedAnswers = List.of();

        // when
        List<Answer> answers = underTest.findAnswersByQuestionId(questionId);

        // then
        assertThat(answers).usingRecursiveComparison().isEqualTo(expectedAnswers);
    }
}
