package com.judele.raci.repository;

import com.judele.raci.entity.Question;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT q.currentQuestion FROM Quiz q where q.status = 'OPEN'")
    Optional<Question> findCurrentQuestionInOpenQuiz();
}
