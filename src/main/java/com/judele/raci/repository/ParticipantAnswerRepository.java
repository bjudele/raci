package com.judele.raci.repository;

import com.judele.raci.entity.ParticipantAnswer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantAnswerRepository extends JpaRepository<ParticipantAnswer, Integer> {

    @Query("select p from ParticipantAnswer  p where p.participant.email = :participantEmail and p.question.quiz.currentQuestion = p.question")
    Optional<ParticipantAnswer> findParticipantAnswerForCurrentQuestion(String participantEmail);
}
