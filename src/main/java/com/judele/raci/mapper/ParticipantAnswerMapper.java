package com.judele.raci.mapper;

import com.judele.raci.dto.ParticipantAnswerDto;
import com.judele.raci.entity.ParticipantAnswer;
import com.judele.raci.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class ParticipantAnswerMapper {

    public ParticipantAnswerDto map(ParticipantAnswer participantAnswer) {
        return ParticipantAnswerDto.builder()
            .productManagerAnswer(participantAnswer.getProductManagerAnswer().getSymbol())
            .engineeringManagerAnswer(participantAnswer.getEngineeringManagerAnswer().getSymbol())
            .designerAnswer(participantAnswer.getDesignerAnswer().getSymbol())
            .driAnswer(participantAnswer.getDriAnswer().getSymbol())
            .developerAnswer(participantAnswer.getDeveloperAnswer().getSymbol())
            .pointsEarned(participantAnswer.getPointsEarned())
            .build();
    }

    public ParticipantAnswerDto map(Question question) {
        return ParticipantAnswerDto.builder()
            .productManagerAnswer(question.getProductManagerAnswer().getSymbol())
            .engineeringManagerAnswer(question.getEngineeringManagerAnswer().getSymbol())
            .designerAnswer(question.getDesignerAnswer().getSymbol())
            .driAnswer(question.getDriAnswer().getSymbol())
            .developerAnswer(question.getDeveloperAnswer().getSymbol())
            .build();
    }
}
