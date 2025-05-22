package org.workshop.microphoneschedulerapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.dto.ActorOwnSceneCustomListDTO;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.User;
import org.workshop.microphoneschedulerapi.domain.model.ActorOwnSceneCustom;
import org.workshop.microphoneschedulerapi.repository.PersonageRepository;
import org.workshop.microphoneschedulerapi.repository.SceneRepository;
import org.workshop.microphoneschedulerapi.repository.Scene_characterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {

    private PersonageRepository personageRepository;
    private Scene_characterRepository scene_characterRepository;

    @Autowired
    public ActorService(PersonageRepository personageRepository, Scene_characterRepository scene_characterRepository) {
        this.personageRepository = personageRepository;
        this.scene_characterRepository = scene_characterRepository;
    }

    public ActorOwnSceneCustomListDTO getActorOwnSceneCustomListDTO(User user, String playName) {


        List<Personage> allPersonage = personageRepository.findAll();
        List<Personage> allFoundPersonageMatchingUser = new ArrayList<>();
        List<Personage> allMatchingPersonage = new ArrayList<>();
        for (Personage personage : allPersonage) {
            if(personage.getActor().getUser().getUsername().equals(user.getUsername())) {
                allFoundPersonageMatchingUser.add(personage);
            }
        }

        for(Personage personage : allFoundPersonageMatchingUser) {
            if(scene_characterRepository.existsScene_charactersByPersonage(personage)){
                if(personage.getScene_characters().get(0).getScene().getPlay().getPlayName().equals(playName)) {
                    allMatchingPersonage.add(personage);
                }
            }
        }

        List<ActorOwnSceneCustom> customList = new ArrayList<>();
        for(Personage personage : allMatchingPersonage) {
                ActorOwnSceneCustom actorOwnSceneCustom = ActorOwnSceneCustom.builder()
                        .sceneId(personage.getScene_characters().get(0).getScene().getSceneId())
                        .actNumber(personage.getScene_characters().get(0).getScene().getActNumber())
                        .sceneNumber(personage.getScene_characters().get(0).getScene().getSceneNumber())
                        .personageName(personage.getPersonageName())
                        .microphoneId(personage.getScene_characters().get(0).getMicrophone().getMicrophoneId())
                        .microphoneName(personage.getScene_characters().get(0).getMicrophone().getMicrophoneName())
                        .build();
                customList.add(actorOwnSceneCustom);
        }
        ActorOwnSceneCustomListDTO actorOwnSceneCustomListDTO = new ActorOwnSceneCustomListDTO();
        actorOwnSceneCustomListDTO.setActorScenes(customList);

        return actorOwnSceneCustomListDTO;
    }
}
