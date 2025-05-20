package org.workshop.microphoneschedulerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.domain.entity.Scene_character;

import java.util.List;

@Repository
public interface Scene_characterRepository extends JpaRepository<Scene_character, Long> {

    //Scene_character findScene_characterByscene_id(int sceneId);

}
