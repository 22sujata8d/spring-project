package com.example.demo.question;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerRepository;
import com.example.demo.answer.AnswerService;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.question.dtos.QuestionAnswerDto;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerRepository answerRepository;
    private QuestionService underTest;

    @BeforeEach
    void setUp() {

        underTest = new QuestionService(questionRepository, answerRepository);
    }

    @Test
    void addNewQuestion() {
        // given
        Question question = new Question(
                "what is physics?",
                "Airy"
        );
        question.setDateAdded(LocalDate.now());

        Question mockQuestion = question;
        mockQuestion.setId(1L);

        given(questionRepository.save(any())).willReturn(mockQuestion);

        // when
        underTest.addNewQuestion(question);

        // then
        ArgumentCaptor<Question> questionArgumentCaptor =
                ArgumentCaptor.forClass(Question.class);

        verify(questionRepository)
                .save(questionArgumentCaptor.capture());

        Question capturedQuestion = questionArgumentCaptor.getValue();

        assertThat(capturedQuestion).isEqualTo(question);
    }

    @Test
    void willThrowExceptionWhenEmptyQuestionIsAdded() {
        // given
        Question question = new Question();

        // when
        // then
        assertThatThrownBy(() -> underTest.addNewQuestion(question))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("full name or question should not be empty");

        verify(questionRepository, never()).save(any());
    }

    @Test
    void getQuestionAnswersByQuestionId() {
        // given
        Long questionId = 1L;
        Question question = new Question(
                "What is the name of Lion King?",
                "Simba"
        );

        question.setId(questionId);
        question.setDateAdded(LocalDate.now());

        Answer answer = new Answer(
                "Lion King",
                "LionKing is the king of Lions"
        );

        answer.setId(1L);
        answer.setQuestionId(questionId);
        answer.setDateAdded(LocalDate.now());

        given(questionRepository.selectQuestionExists(questionId)).willReturn(true);
        given(questionRepository.findById(questionId)).willReturn(Optional.of(question));
        given(answerRepository.findAnswersByQuestionId(questionId)).willReturn(List.of(answer));

        // then
        underTest.getQuestionAnswersByQuestionId(questionId);
    }

    @Test
    void willThrowWhenGetQuestionAnswersByQuestionId() {
        // given
        Long questionId = 1L;

        given(questionRepository.selectQuestionExists(questionId)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> underTest.getQuestionAnswersByQuestionId(questionId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("question with id %d not found", questionId));

        verify(questionRepository, never()).findById(any());
        verify(answerRepository, never()).findAnswersByQuestionId(questionId);
    }

    @Test
    void getAllQuestionsAnswersWhenNoQuestionAnswerIsPresent() {
        // given
        List<QuestionAnswerDto> expectedQuestionsAnswers = new ArrayList<>();

        given(questionRepository.findAll()).willReturn(List.of());

        // when
        List<QuestionAnswerDto> actualQuestionsAnswers = underTest.getAllQuestionsAnswers();

        // then
        assertThat(actualQuestionsAnswers).usingRecursiveComparison().isEqualTo(expectedQuestionsAnswers);
        verify(answerRepository, never()).findAnswersByQuestionId(any());
    }

    @Test
    void getAllQuestionsAnswers() {
        // given
        Question question = new Question(
                "What is a calculator",
                "Maths Genius"
        );

        question.setDateAdded(LocalDate.now());
        question.setId(1L);

        Answer answer = new Answer(
                "Skylar",
                "Calculator is a machine"
        );

        answer.setId(1L);
        answer.setQuestionId(1L);
        answer.setDateAdded(LocalDate.now());

        QuestionAnswerDto dto = new QuestionAnswerDto(
          new com.example.demo.question.dtos.Question(
                  question.getId(),
                  question.getFullName(),
                  question.getQuestion(),
                  question.getDateAdded()
          ), List.of(
                  new com.example.demo.question.dtos.Answer(
                          answer.getId(),
                          answer.getFullName(),
                          answer.getAnswer(),
                          answer.getDateAdded()
                  )
        ));
        List<QuestionAnswerDto> expectedQuestionsAnswers = List.of(dto);

        answer.setDateAdded(LocalDate.now());
        answer.setQuestionId(1L);

        given(questionRepository.findAll()).willReturn(List.of(question));
        given(answerRepository.findAnswersByQuestionId(any())).willReturn(List.of(answer));

        // when
        List<QuestionAnswerDto> actualQuestionsAnswers = underTest.getAllQuestionsAnswers();

        // then
        assertThat(actualQuestionsAnswers).usingRecursiveComparison().isEqualTo(expectedQuestionsAnswers);
    }
}