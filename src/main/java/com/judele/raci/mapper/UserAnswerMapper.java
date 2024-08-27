package com.judele.raci.mapper;

import com.judele.raci.dto.ParticipantAnswerDto;
import com.judele.raci.entity.Participant;
import com.judele.raci.entity.Question;
import com.judele.raci.entity.ParticipantAnswer;
import com.judele.raci.enums.RaciRole;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerMapper {

    public ParticipantAnswer map(Question question, ParticipantAnswerDto participantAnswerDto, Participant participant) {
        return ParticipantAnswer.builder()
            .participant(participant)
            .question(question)
            .productManagerAnswer(RaciRole.fromSymbol(participantAnswerDto.getProductManagerAnswer()))
            .engineeringManagerAnswer(RaciRole.fromSymbol(participantAnswerDto.getEngineeringManagerAnswer()))
            .designerAnswer(RaciRole.fromSymbol(participantAnswerDto.getDesignerAnswer()))
            .driAnswer(RaciRole.fromSymbol(participantAnswerDto.getDriAnswer()))
            .developerAnswer(RaciRole.fromSymbol(participantAnswerDto.getDeveloperAnswer()))
            .build();
    }

}
