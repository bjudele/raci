package com.judele.raci.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participantId;
    private String email;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "participant_quiz",
        joinColumns = @JoinColumn(name = "participantId"),
        inverseJoinColumns = @JoinColumn(name = "quizId")
    )
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "participant")
    private List<ParticipantAnswer> participantAnswers;
}
