package org.workshop.microphoneschedulerapi.service;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.dto.DirectorOverviewDTO;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.domain.entity.Scene_character;
import org.workshop.microphoneschedulerapi.domain.model.DirectorOverviewCustom;
import org.workshop.microphoneschedulerapi.repository.PlayRepository;
import org.workshop.microphoneschedulerapi.repository.SceneRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorService {

    private PlayRepository playRepository;
    private SceneRepository sceneRepository;

    @Autowired
    public DirectorService(PlayRepository playRepository, SceneRepository sceneRepository) {
        this.playRepository = playRepository;
        this.sceneRepository = sceneRepository;
    }

    public DirectorOverviewDTO getOverview( String playName) {

        List<Scene> allScenesInPlay = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));
        List<Scene_character> scenesInPlay = new ArrayList<>();
        for (Scene scene : allScenesInPlay) {
            for (Scene_character scene_character : scene.getScene_characters()) {
                scenesInPlay.add(scene_character);
            }
        }
        DirectorOverviewDTO directorOverviewDTO = new DirectorOverviewDTO();

        List<DirectorOverviewCustom> directorOverviewCustomList = new ArrayList<>();
        for (Scene_character scene_character : scenesInPlay) {
            if(scene_character.getMicrophone() != null) {
                DirectorOverviewCustom directorOverviewCustom = DirectorOverviewCustom.builder()

                        .microphoneName(scene_character.getMicrophone().getMicrophoneName())
                        .microphoneId(scene_character.getMicrophone().getMicrophoneId())
                        .sceneId(scene_character.getScene().getSceneId())
                        .sceneNumber(scene_character.getScene().getSceneNumber())
                        .actNumber(scene_character.getScene().getActNumber())
                        .sceneName(scene_character.getScene().getSceneName())
                        .personageName(scene_character.getPersonage().getPersonageName())
                        .userName(scene_character.getPersonage().getActor().getUser().getUsername())
                        .build();
                directorOverviewCustomList.add(directorOverviewCustom);

            }else{

                DirectorOverviewCustom directorOverviewCustom = DirectorOverviewCustom.builder()


                        .sceneId(scene_character.getScene().getSceneId())
                        .sceneNumber(scene_character.getScene().getSceneNumber())
                        .actNumber(scene_character.getScene().getActNumber())
                        .sceneName(scene_character.getScene().getSceneName())
                        .personageName(scene_character.getPersonage().getPersonageName())
                        .userName(scene_character.getPersonage().getActor().getUser().getUsername())
                        .build();
                directorOverviewCustomList.add(directorOverviewCustom);

            }


        }

        directorOverviewDTO.setDirectorList(directorOverviewCustomList);

        return directorOverviewDTO;
    }


}
