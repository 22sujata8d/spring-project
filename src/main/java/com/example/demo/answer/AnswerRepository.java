package com.example.demo.answer;

import com.example.demo.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository
        extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a WHERE a.questionId = ?1")
    List<Answer> findAnswersByQuestionId(Long questionId);
}
