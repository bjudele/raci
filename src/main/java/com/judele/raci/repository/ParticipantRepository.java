package com.judele.raci.repository;

import com.judele.raci.entity.Participant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Optional<Participant> findByEmail(String email);
}
