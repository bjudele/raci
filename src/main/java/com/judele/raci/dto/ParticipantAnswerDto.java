package com.judele.raci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantAnswerDto {

    private String productManagerAnswer;
    private String engineeringManagerAnswer;
    private String designerAnswer;
    private String driAnswer;
    private String developerAnswer;
    private Integer pointsEarned;
}
