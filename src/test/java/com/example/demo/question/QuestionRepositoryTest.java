package com.example.demo.question;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkQuestionExistsIfQuestionIsPresent() {
        // given
        Question question = new Question(
          "Where is Earth?", "Great Astronomer"
        );

        question.setDateAdded(LocalDate.now());
        question.setId(1L);

        underTest.save(question);

        // when
        boolean exists = underTest.selectQuestionExists(question.getId());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void checkQuestionExistsIfQuestionIsAbsent() {
        // given
       Long questionId = 1L;

        // when
        boolean exists = underTest.selectQuestionExists(questionId);

        // then
        assertThat(exists).isFalse();
    }

}
