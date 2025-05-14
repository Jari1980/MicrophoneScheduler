package org.workshop.microphoneschedulerapi.repository;

import jakarta.transaction.Transactional;
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
@Transactional
public interface SceneRepository extends JpaRepository<Scene, Integer> {

    Optional<List<Scene>> findScenesByPlay(Play play);

    @Query(value="select u.characters from Scene u where u.sceneId = :sceneId")
    List<Personage> getCharactersBySceneId(int sceneId);

    void deleteAllByPlay(Play play);

    List<Scene> findAllByPlay(Play play);

    @Query(value="select s.sceneId from Scene s where s.play.playName = :playName")
    List<Integer> findSceneIdsByPlayName(@NonNull String playName);

    //@Query(value="select s from Scene where s.sceneId = :sceneId")
    Scene findSceneBySceneId(int sceneId);



}


