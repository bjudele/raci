package com.judele.raci.service;

import com.judele.raci.dto.ParticipantAnswerDto;
import com.judele.raci.dto.ParticipantScoreDto;
import com.judele.raci.dto.QuestionDto;
import com.judele.raci.dto.ScoreDto;
import com.judele.raci.entity.Participant;
import com.judele.raci.entity.Question;
import com.judele.raci.entity.Quiz;
import com.judele.raci.entity.ParticipantAnswer;
import com.judele.raci.enums.QuizStatus;
import com.judele.raci.mapper.ParticipantAnswerMapper;
import com.judele.raci.mapper.QuestionDtoBuilder;
import com.judele.raci.mapper.UserAnswerMapper;
import com.judele.raci.repository.ParticipantAnswerRepository;
import com.judele.raci.repository.ParticipantRepository;
import com.judele.raci.repository.QuestionRepository;
import com.judele.raci.repository.QuizRepository;
import com.judele.raci.repository.UserAnswerRepository;
import java.util.Comparator;
import java.util.*;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final QuestionDtoBuilder questionDtoBuilder;
    private final UserAnswerMapper userAnswerMapper;
    private final ScoreCalculator scoreCalculator;
    private final QuizRepository quizRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantAnswerRepository participantAnswerRepository;
    private final ParticipantAnswerMapper participantAnswerMapper;

    public Optional<QuestionDto> getCurrentQuestionDto(String participantEmail) {
        Optional<Question> optionalQuestion = questionRepository.findCurrentQuestionInOpenQuiz();
        return optionalQuestion.map(question -> questionDtoBuilder.build(question, participantEmail));
    }

    public Optional<QuestionDto> getCurrentQuestionDto() {
        Optional<Question> optionalQuestion = questionRepository.findCurrentQuestionInOpenQuiz();
        return optionalQuestion.map(questionDtoBuilder::buildForAdmin);
    }

    public void recordUserAnswer(ParticipantAnswerDto participantAnswerDto, String userEmail) {
        Optional<Question> optionalQuestion = questionRepository.findCurrentQuestionInOpenQuiz();
        if (optionalQuestion.isEmpty()) {
            throw new RuntimeException("Question not found");
        }
        Optional<Participant> optionalParticipant = participantRepository.findByEmail(userEmail);
        if (optionalParticipant.isEmpty()) {
            throw new RuntimeException("Participant not found");
        }
        ParticipantAnswer participantAnswer = userAnswerMapper.map(optionalQuestion.get(), participantAnswerDto,
            optionalParticipant.get());
        evaluateAnswer(optionalQuestion.get(), participantAnswer);
        userAnswerRepository.save(participantAnswer);
    }

    public void lock(QuestionDto questionDto) {
        questionDto.setLocked(true);
    }

    private void evaluateAnswer(Question question, ParticipantAnswer participantAnswer) {
        Integer pointsEarned = scoreCalculator.calculatePoints(question, participantAnswer);
        participantAnswer.setPointsEarned(pointsEarned);
    }


    public void registerToActiveQuiz(String authenticatedUserEmail, String authenticatedUserName) {
        Optional<Quiz> optionalQuiz = quizRepository.findQuizByStatus(QuizStatus.OPEN);
        if (optionalQuiz.isEmpty()) {
            throw new RuntimeException("No quiz open at this time");
        }
        if (participantAlreadyRegistered(authenticatedUserEmail, optionalQuiz.get())) {
            System.out.println("Participant already registered: " + authenticatedUserEmail);
            return;
        }
        Participant participant = Participant.builder()
            .email(authenticatedUserEmail)
            .name(authenticatedUserName)
            .quizzes(List.of(optionalQuiz.get()))
            .build();
        participantRepository.save(participant);
    }

    private boolean participantAlreadyRegistered(String authenticatedUserEmail, Quiz quiz) {
        return quiz.getParticipants().stream()
            .anyMatch(participant -> participant.getEmail().equals(authenticatedUserEmail));
    }

    public Boolean allParticipantsRespondedToCurrentQuestion() {
        return quizRepository.findQuizByStatus(QuizStatus.OPEN)
            .map(quiz -> quiz.getCurrentQuestion().getParticipantAnswers().size() == quiz.getParticipants().size())
            .orElse(false);
    }

    public Optional<ParticipantAnswerDto> getCurrentAnswerDto(String authenticatedUserEmail) {
        Optional<ParticipantAnswer> optionalParticipantAnswer = participantAnswerRepository.findParticipantAnswerForCurrentQuestion(
            authenticatedUserEmail);
        ParticipantAnswer participantAnswer = optionalParticipantAnswer.get();
        return Optional.of(participantAnswerMapper.map(participantAnswer));

    }

    public ParticipantAnswerDto getCorrectAnswerDto() {
        Optional<Question> optionalQuestion = questionRepository.findCurrentQuestionInOpenQuiz();
        if (optionalQuestion.isEmpty()) {
            return new ParticipantAnswerDto();
        }
        return participantAnswerMapper.map(optionalQuestion.get());
    }

    public ScoreDto getCurrentScoreDto() {
        List<ParticipantAnswer> participantAnswers = userAnswerRepository.findByOpenQuiz();
        Map<Participant, Integer> participantScores = participantAnswers.stream()
            .collect(Collectors.toMap(
                ParticipantAnswer::getParticipant,
                ParticipantAnswer::getPointsEarned,
                Integer::sum));
        Set<Integer> maxTotalValues = participantScores.values().stream()
            .collect(Collectors.toCollection(() -> new TreeSet<Integer>(Comparator.reverseOrder()))).stream().limit(3)
            .collect(Collectors.toCollection(LinkedHashSet::new));

        List<ParticipantScoreDto> participantScoreDtos = participantScores.entrySet().stream()
            .map(entry -> new ParticipantScoreDto(entry.getKey().getName() + " (" + entry.getKey().getEmail() + ")",
                entry.getValue()))
            .filter(x -> maxTotalValues.contains(x.getScore()))
            .sorted(Comparator.comparing(ParticipantScoreDto::getScore).reversed())
            .collect(Collectors.toCollection(ArrayList::new));

        return ScoreDto.builder()
            .participantScores(participantScoreDtos)
            .build();
    }

    public boolean nextQuestion() {
        Optional<Quiz> optionalQuiz = quizRepository.findQuizByStatus(QuizStatus.OPEN);
        if (optionalQuiz.isEmpty()) {
            return false;
        }
        Quiz quiz = optionalQuiz.get();

        if (quiz.getCurrentQuestion().stillNeedsAnswers()) {
            return true;
        }

        Optional<Question> optionalNextQuestion = quiz.getQuestions().stream()
            .filter(q -> Integer.valueOf(quiz.getCurrentQuestion().getQuestionIndex() + 1).equals(q.getQuestionIndex()))
            .findFirst();
        if (optionalNextQuestion.isEmpty()) {
            return false;
        }
        quiz.setCurrentQuestion(optionalNextQuestion.get());
        quizRepository.save(quiz);
        return true;
    }

    public boolean participantNotRegistered(String authenticatedUserEmail) {
        Optional<Participant> optionalParticipant = participantRepository.findByEmail(authenticatedUserEmail);
        return optionalParticipant.isEmpty();
    }

    public boolean noQuizParticipants() {
        return participantRepository.findAll().isEmpty();

    }
}
