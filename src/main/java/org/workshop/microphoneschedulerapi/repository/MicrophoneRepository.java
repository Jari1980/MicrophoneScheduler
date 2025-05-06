package org.workshop.microphoneschedulerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;

@Repository
public interface MicrophoneRepository extends JpaRepository<Microphone, Integer> {
}
