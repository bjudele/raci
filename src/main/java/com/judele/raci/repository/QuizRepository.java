package com.judele.raci.repository;

import com.judele.raci.entity.Quiz;
import com.judele.raci.enums.QuizStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Optional<Quiz> findQuizByStatus(QuizStatus status);
}
