package com.judele.raci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private Integer questionId;
    private String questionBody;
    private String currentQuestionNumber;
    private String totalQuestionNumber;
    private String progressPercentage;
    private boolean isLocked;
    private boolean showResults;

    private String productManagerRightAnswer;
    private String engineeringManagerRightAnswer;
    private String designerRightAnswer;
    private String driRightAnswer;
    private String developerRightAnswer;
}
