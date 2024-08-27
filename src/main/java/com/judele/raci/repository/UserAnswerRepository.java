package com.judele.raci.repository;

import com.judele.raci.entity.ParticipantAnswer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAnswerRepository extends JpaRepository<ParticipantAnswer, Integer> {

    @Query("select p from ParticipantAnswer p where p.question.quiz.status = com.judele.raci.enums.QuizStatus.OPEN")
    List<ParticipantAnswer> findByOpenQuiz();
}
