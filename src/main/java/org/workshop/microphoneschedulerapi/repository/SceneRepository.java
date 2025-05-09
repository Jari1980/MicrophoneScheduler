package org.workshop.microphoneschedulerapi.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.Play;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;

import java.util.List;
import java.util.Optional;

@Repository
public interface SceneRepository extends JpaRepository<Scene, Integer> {

    Optional<List<Scene>> findScenesByPlay(Play play);

    @Query(value="select u.characters from Scene u where u.sceneId = :sceneId")
    List<Personage> getCharactersBySceneId(int sceneId);

}


