package com.judele.raci.controller;

import com.judele.raci.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class RestApiController {


    private final QuizService quizService;

    @GetMapping
    public Boolean getStatus() {

        Boolean status = quizService.allParticipantsRespondedToCurrentQuestion();
        System.out.println("Status: " + status);
        return status;
    }
}
