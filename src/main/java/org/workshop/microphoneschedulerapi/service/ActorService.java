package org.workshop.microphoneschedulerapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.dto.ActorOwnSceneCustomListDTO;
import org.workshop.microphoneschedulerapi.domain.dto.CustomUser;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.Scene_character;
import org.workshop.microphoneschedulerapi.domain.entity.User;
import org.workshop.microphoneschedulerapi.domain.model.ActorOwnSceneCustom;
import org.workshop.microphoneschedulerapi.repository.PersonageRepository;
import org.workshop.microphoneschedulerapi.repository.Scene_characterRepository;
import org.workshop.microphoneschedulerapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {

    private PersonageRepository personageRepository;
    private Scene_characterRepository scene_characterRepository;
    private UserRepository userRepository;

    @Autowired
    public ActorService(PersonageRepository personageRepository, Scene_characterRepository scene_characterRepository,
                        UserRepository userRepository) {
        this.personageRepository = personageRepository;
        this.scene_characterRepository = scene_characterRepository;
        this.userRepository = userRepository;
    }

    public ActorOwnSceneCustomListDTO getActorOwnSceneCustomListDTO(User user, String playName) {

        List<Personage> allPersonage = personageRepository.findAll();
        List<Scene_character> allSceneCharacter = scene_characterRepository.findAll();

        //Filtering by username
        List<Personage> allFoundPersonageMatchingUser = new ArrayList<>();
        for (Personage personage : allPersonage) {
            if(personage.getActor().getUser().getUsername().equals(user.getUsername())) {
                allFoundPersonageMatchingUser.add(personage);
            }
        }

        //Filtering by playName
        List<Personage> allMatchingPersonage = new ArrayList<>();
        for(Personage personage : allFoundPersonageMatchingUser) {
            for(Scene_character scene_character : allSceneCharacter) {
                if(scene_character.getPersonage().getPersonageId() == personage.getPersonageId()) {
                    if(scene_character.getScene().getPlay().getPlayName().equals(playName)) {
                        allMatchingPersonage.add(personage);
                    }
                }
            }
        }

        List<ActorOwnSceneCustom> customList = new ArrayList<>();
        for(Personage personage : allMatchingPersonage) {
            for(Scene_character scene_character : allSceneCharacter) {
                if(scene_character.getPersonage().getPersonageId() == personage.getPersonageId() && scene_character.getScene().getPlay().getPlayName().equals(playName)) {
                    if(scene_character.getMicrophone() != null) {
                        ActorOwnSceneCustom actorOwnSceneCustom = ActorOwnSceneCustom.builder()
                                .sceneId(scene_character.getScene().getSceneId())
                                .actNumber(scene_character.getScene().getActNumber())
                                .sceneNumber(scene_character.getScene().getSceneNumber())
                                .sceneName(scene_character.getScene().getSceneName())
                                .personageName(scene_character.getPersonage().getPersonageName())
                                .microphoneId(scene_character.getMicrophone().getMicrophoneId())
                                .microphoneName(scene_character.getMicrophone().getMicrophoneName())
                                .build();
                        customList.add(actorOwnSceneCustom);
                    }
                    else{
                        ActorOwnSceneCustom actorOwnSceneCustom = ActorOwnSceneCustom.builder()
                                .sceneId(scene_character.getScene().getSceneId())
                                .actNumber(scene_character.getScene().getActNumber())
                                .sceneNumber(scene_character.getScene().getSceneNumber())
                                .sceneName(scene_character.getScene().getSceneName())
                                .personageName(scene_character.getPersonage().getPersonageName())
                                //.microphoneId(scene_character.getMicrophone().getMicrophoneId())
                                //.microphoneName(scene_character.getMicrophone().getMicrophoneName())
                                .build();
                        customList.add(actorOwnSceneCustom);
                    }
                }
            }
            break;
        }
        ActorOwnSceneCustomListDTO actorOwnSceneCustomListDTO = new ActorOwnSceneCustomListDTO();
        actorOwnSceneCustomListDTO.setActorScenes(customList);

        return actorOwnSceneCustomListDTO;
    }

    public List<CustomUser> getAllUsers(){
        List<CustomUser> customUserList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for(User user : userList) {
            CustomUser customUser = CustomUser.builder()
                    .userId(user.getUserId())
                    .userName(user.getUsername())
                    .build();
            customUserList.add(customUser);
        }

        return customUserList;
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
