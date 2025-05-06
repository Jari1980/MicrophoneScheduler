package org.workshop.microphoneschedulerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;

@Repository
public interface SceneRepository extends JpaRepository<Scene, Integer> {
}
