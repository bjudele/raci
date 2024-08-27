package com.judele.raci.entity;

import com.judele.raci.enums.QuizStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.servlet.http.Part;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizId;
    private String name;
    private LocalDateTime createdDateTime;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    @Enumerated(value = EnumType.STRING)
    private QuizStatus status;

    @OneToOne
    @JoinColumn
    private Question currentQuestion;

    @ManyToMany(mappedBy = "quizzes")
    private List<Participant> participants;

    private String firstPlaceEmail;
    private String secondPlaceEmail;
    private String thirdPlaceEmail;
    private Integer firstPlaceScore;
    private Integer secondPlaceScore;
    private Integer thirdPlaceScore;
}
