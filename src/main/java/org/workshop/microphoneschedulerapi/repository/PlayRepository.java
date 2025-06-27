package org.workshop.microphoneschedulerapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Play;

import java.time.LocalDate;

@Repository
@Transactional
public interface PlayRepository extends JpaRepository<Play, String> {

    @Modifying
    @Query("UPDATE Play p SET p.premiereDate = :newDate, p.description = :newDescription WHERE p.playName = :playName")
    void updatePlay(String playName, LocalDate newDate, String newDescription);


}
