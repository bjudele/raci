package com.judele.raci.mapper;

import com.judele.raci.dto.QuestionDto;
import com.judele.raci.entity.ParticipantAnswer;
import com.judele.raci.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionDtoBuilder {

    public QuestionDto build(Question question, String participantEmail) {
        int numberOfQuestions = question.getQuiz().getQuestions().size();
        boolean locked = question.getParticipantAnswers().stream()
            .anyMatch(answer -> answer.getParticipant().getEmail().equals(participantEmail));

        return
            QuestionDto.builder()
                .questionId(question.getQuestionId())
                .questionBody(question.getQuestionBody())
                .currentQuestionNumber(String.valueOf(question.getQuestionIndex()))
                .totalQuestionNumber(String.valueOf(numberOfQuestions))
                .isLocked(locked)
                .progressPercentage(getPercentageAsString(question, numberOfQuestions))
                .productManagerRightAnswer(question.getProductManagerAnswer().getSymbol())
                .engineeringManagerRightAnswer(question.getEngineeringManagerAnswer().getSymbol())
                .designerRightAnswer(question.getDesignerAnswer().getSymbol())
                .driRightAnswer(question.getDriAnswer().getSymbol())
                .developerRightAnswer(question.getDeveloperAnswer().getSymbol())
                .showResults(question.getParticipantAnswers().size() == question.getQuiz().getParticipants().size())
                .build();
    }
    public QuestionDto buildForAdmin(Question question) {
        int numberOfQuestions = question.getQuiz().getQuestions().size();

        return
            QuestionDto.builder()
                .questionId(question.getQuestionId())
                .questionBody(question.getQuestionBody())
                .currentQuestionNumber(String.valueOf(question.getQuestionIndex()))
                .totalQuestionNumber(String.valueOf(numberOfQuestions))
                .isLocked(true)
                .progressPercentage(getPercentageAsString(question, numberOfQuestions))
                .productManagerRightAnswer(question.getProductManagerAnswer().getSymbol())
                .engineeringManagerRightAnswer(question.getEngineeringManagerAnswer().getSymbol())
                .designerRightAnswer(question.getDesignerAnswer().getSymbol())
                .driRightAnswer(question.getDriAnswer().getSymbol())
                .developerRightAnswer(question.getDeveloperAnswer().getSymbol())
                .showResults(question.getParticipantAnswers().size() == question.getQuiz().getParticipants().size())
                .build();
    }
    private String getPercentageAsString(Question question, int numberOfQuestions) {
        return String.format("%.2f%%", question.getQuestionIndex() * 100f / numberOfQuestions);
    }
}
