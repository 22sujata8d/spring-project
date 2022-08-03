package com.example.demo.answer;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {
    @Mock private QuestionRepository questionRepository;
    @Mock private AnswerRepository answerRepository;
    private AnswerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AnswerService(questionRepository, answerRepository);
    }

    @Test
    void addAnswerToExistingQuestionId() {
        // given
        Long questionId = 1L;
        Answer answer = new Answer(
                "Name",
                "test answer");
        answer.setQuestionId(questionId);
        answer.setDateAdded(LocalDate.now());
        answer.setQuestionId(1L);

        given(questionRepository.selectQuestionExists(questionId)).willReturn(true);
        given(answerRepository.save(any())).willReturn(answer);

        // when
        underTest.addAnswerByQuestionId(answer, questionId);

        // then
        ArgumentCaptor<Answer> answerArgumentCaptor =
                ArgumentCaptor.forClass(Answer.class);

        verify(answerRepository)
                .save(answerArgumentCaptor.capture());

        Answer capturedAnswer = answerArgumentCaptor.getValue();

        assertThat(capturedAnswer).isEqualTo(answer);
    }

    @Test
    void willThrowExceptionWhenQuestionIdNotExists() {
        // given
        Long questionId = 1L;
        Answer answer = new Answer(
                "Name",
                "test answer");
        answer.setQuestionId(questionId);
        answer.setDateAdded(LocalDate.now());
        answer.setQuestionId(1L);

        // when
        // then
        assertThatThrownBy(() -> underTest.addAnswerByQuestionId(answer, questionId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("question with question id %d not found", questionId));

        verify(answerRepository, never()).save(any());
    }

    @Test
    void willThrowExceptionWhenAnswerIsEmpty() {
        // given
        Long questionId = 1L;
        Answer answer = new Answer();

        // when
        // then
        assertThatThrownBy(() -> underTest.addAnswerByQuestionId(answer, questionId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("full name or answer should not be empty");

        verify(questionRepository, never()).selectQuestionExists(any());
        verify(answerRepository, never()).save(any());
    }

    @Test
    void willThrowExceptionWhenFullNameInAnswerIsEmpty() {
        // given
        Long questionId = 1L;
        Answer answer = new Answer(
                "",
                "this is the answer"
        );

        // when
        // then
        assertThatThrownBy(() -> underTest.addAnswerByQuestionId(answer, questionId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("full name or answer should not be empty");

        verify(questionRepository, never()).selectQuestionExists(any());
        verify(answerRepository, never()).save(any());
    }
}
