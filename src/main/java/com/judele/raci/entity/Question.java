package com.judele.raci.entity;

import com.judele.raci.enums.RaciRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;
    private Integer questionIndex;
    private String questionBody;
    @Enumerated(value = EnumType.STRING)
    private RaciRole productManagerAnswer;
    @Enumerated(value = EnumType.STRING)
    private RaciRole engineeringManagerAnswer;
    @Enumerated(value = EnumType.STRING)
    private RaciRole designerAnswer;
    @Enumerated(value = EnumType.STRING)
    private RaciRole driAnswer;
    @Enumerated(value = EnumType.STRING)
    private RaciRole developerAnswer;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<ParticipantAnswer> participantAnswers;

    public Boolean stillNeedsAnswers() {
        return this.getParticipantAnswers().size() != this.getQuiz().getParticipants().size();
    }
}
