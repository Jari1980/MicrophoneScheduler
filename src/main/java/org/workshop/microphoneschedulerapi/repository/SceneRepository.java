package org.workshop.microphoneschedulerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Play;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;

import java.util.List;
import java.util.Optional;

@Repository
public interface SceneRepository extends JpaRepository<Scene, Integer> {

    Optional<List<Scene>> findScenesByPlay(Play play);
}
