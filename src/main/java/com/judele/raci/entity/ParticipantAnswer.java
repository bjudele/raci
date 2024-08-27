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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userAnswerId;

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

    private Integer pointsEarned;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private Question question;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private Participant participant;

}
