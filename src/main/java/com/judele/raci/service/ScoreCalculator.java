package com.judele.raci.service;

import com.judele.raci.entity.Question;
import com.judele.raci.entity.ParticipantAnswer;
import org.springframework.stereotype.Service;

@Service
public class ScoreCalculator {

    public Integer calculatePoints(Question question, ParticipantAnswer participantAnswer) {
        Integer pointsEarned = 0;
        if (question.getEngineeringManagerAnswer().equals(participantAnswer.getEngineeringManagerAnswer())) {
            pointsEarned++;
        }
        if (question.getProductManagerAnswer().equals(participantAnswer.getProductManagerAnswer())) {
            pointsEarned++;
        }
        if (question.getDesignerAnswer().equals(participantAnswer.getDesignerAnswer())) {
            pointsEarned++;
        }
        if (question.getDriAnswer().equals(participantAnswer.getDriAnswer())) {
            pointsEarned++;
        }
        if (question.getDeveloperAnswer().equals(participantAnswer.getDeveloperAnswer())) {
            pointsEarned++;
        }
        return pointsEarned;
    }
}
