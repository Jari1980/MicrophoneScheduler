package org.workshop.microphoneschedulerapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MicrophoneRepository extends JpaRepository<Microphone, Integer> {

    List<Microphone> findByMicrophoneName(String microphoneName);

    @Modifying
    @Query("UPDATE Microphone m SET m.microphoneName = :newName WHERE m.microphoneId = :id")
    void updateMicrophone(int id, String newName);
}
