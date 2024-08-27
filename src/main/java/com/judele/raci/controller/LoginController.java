package com.judele.raci.controller;

import com.judele.raci.dto.ParticipantAnswerDto;
import com.judele.raci.dto.ScoreDto;
import com.judele.raci.service.QuizService;
import com.judele.raci.dto.QuestionDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"participantAnswerDto"})
public class LoginController {

    private final QuizService quizService;


    @GetMapping("/home")
    public String home() {
        return "home";
    }


    @GetMapping("/quiz")
    public String quiz(Model model, @ModelAttribute(name = "authenticatedUserEmail") String authenticatedUserEmail) {
        if(quizService.participantNotRegistered(authenticatedUserEmail)){
            return "redirect:/home";
        }
        Optional<QuestionDto> optionalQuestionDto = quizService.getCurrentQuestionDto(authenticatedUserEmail);
        if (optionalQuestionDto.isEmpty()) {
            System.out.println("Something went wrong for " + authenticatedUserEmail);
            return "redirect:/home";
        }
        model.addAttribute("questionDto", optionalQuestionDto.get());

        if (optionalQuestionDto.get().isLocked()) {
            Optional<ParticipantAnswerDto> optionalParticipantAnswerDto = quizService.getCurrentAnswerDto(
                authenticatedUserEmail);
            if (optionalParticipantAnswerDto.isEmpty()) {
                throw new RuntimeException("something went wrong for " + authenticatedUserEmail);
            }
            model.addAttribute("participantAnswerDto", optionalParticipantAnswerDto.get());
            if (optionalQuestionDto.get().isShowResults()) {
                //score
                ScoreDto scoreDto = quizService.getCurrentScoreDto();
                model.addAttribute("scoreDto", scoreDto);

            }

        } else {
            model.addAttribute("participantAnswerDto", new ParticipantAnswerDto());
        }

        return "quiz";
    }


    @PostMapping("/submitAnswer")
    public String submitAnswer(@ModelAttribute("userAnswerDto") ParticipantAnswerDto participantAnswerDto,
        @ModelAttribute("questionDto") QuestionDto questionDto,
        @ModelAttribute(name = "authenticatedUserEmail") String authenticatedUserEmail) {
        quizService.recordUserAnswer(participantAnswerDto, authenticatedUserEmail);
        return "redirect:/quiz";
    }

    @PostMapping("/nextQuestion")
    public String nextQuestion(Model model) {
        boolean hasNextQuestion = quizService.nextQuestion();
        if (hasNextQuestion) {
            return "redirect:/quiz";
        }
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String result(Model model) {
        ScoreDto scoreDto = quizService.getCurrentScoreDto();
        model.addAttribute("scoreDto", scoreDto);
        return "result";
    }

    @PostMapping("/registerToQuiz")
    public String registerToQuiz(@ModelAttribute(name = "authenticatedUserEmail") String authenticatedUserEmail,
        @ModelAttribute(name = "authenticatedUserName") String authenticatedUserName) {
        quizService.registerToActiveQuiz(authenticatedUserEmail, authenticatedUserName);
        return "redirect:/quiz";
    }

    @GetMapping("/admin")
    public String admin(@ModelAttribute(name = "authenticatedUserEmail") String authenticatedUserEmail, Model model) {
        if (!authenticatedUserEmail.equals("bogdan.judele@joinhandshake.com")) {
            return "no";
        }
        if(quizService.noQuizParticipants()){
            return "redirect:/home";
        }
        Optional<QuestionDto> optionalQuestionDto = quizService.getCurrentQuestionDto();
        if (optionalQuestionDto.isEmpty()) {
            System.out.println("Something went wrong for " + authenticatedUserEmail);
            return "redirect:/home";
        }
        model.addAttribute("questionDto", optionalQuestionDto.get());


        if (optionalQuestionDto.get().isShowResults()) {
            //score
            ScoreDto scoreDto = quizService.getCurrentScoreDto();
            model.addAttribute("scoreDto", scoreDto);
            ParticipantAnswerDto correctAnswerDto = quizService.getCorrectAnswerDto();
            model.addAttribute("participantAnswerDto", correctAnswerDto);
        }else {
            model.addAttribute("participantAnswerDto", new ParticipantAnswerDto());
        }

        return "admin";
    }

    @ModelAttribute
    public void addUserToModel(Model model, @AuthenticationPrincipal DefaultOAuth2User user) {
        if (user != null) {
            model.addAttribute("authenticatedUserEmail", user.getAttribute("email"));
            model.addAttribute("authenticatedUserName", user.getAttribute("name"));
        }
    }

    @ModelAttribute("participantAnswerDto")
    public ParticipantAnswerDto userAnswerDto() {
        return new ParticipantAnswerDto(); // Inițializează userAnswerDto dacă nu există
    }

}
