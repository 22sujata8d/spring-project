package com.example.demo.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository
        extends JpaRepository<Question, Long> {
    @Query("" +
            "SELECT CASE WHEN COUNT(q) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Question q " +
            "WHERE q.id = ?1"
    )
    Boolean selectQuestionExists(Long questionId);
}
