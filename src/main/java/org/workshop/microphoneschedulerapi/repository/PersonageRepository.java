package org.workshop.microphoneschedulerapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;

@Repository
public interface PersonageRepository extends JpaRepository<Personage, Integer> {



}
