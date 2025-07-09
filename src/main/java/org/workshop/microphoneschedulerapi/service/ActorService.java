package org.workshop.microphoneschedulerapi.service;


import jakarta.transaction.Transactional;
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

        //List<Personage> allPersonage = personageRepository.findAll();
        List<Scene_character> allSceneCharacter = scene_characterRepository.findAll();

        List<Scene_character> sceneCharactersWithUser = new ArrayList<>();

        for(Scene_character sc : allSceneCharacter) {
            if(sc.getPersonage().getActor() != null){
                if(sc.getPersonage().getActor().getUser().equals(user)) {
                    sceneCharactersWithUser.add(sc);
                }
            }
        }

        List<ActorOwnSceneCustom> customList = new ArrayList<>();

        for(Scene_character scene_character : sceneCharactersWithUser) {
            if(scene_character.getPersonage().getActor() != null) {
                if(scene_character.getPersonage().getActor().getUser().equals(user) && scene_character.getScene().getPlay().getPlayName().equals(playName)) {
                    if(scene_character.getMicrophone() != null) {
                        ActorOwnSceneCustom actorOwnSceneCustom = ActorOwnSceneCustom.builder()
                                .sceneId(scene_character.getScene().getSceneId())
                                .actNumber(scene_character.getScene().getActNumber())
                                .sceneNumber(scene_character.getScene().getSceneNumber())
                                .sceneName(scene_character.getScene().getSceneName())
                                .personageName(scene_character.getPersonage().getPersonageName())
                                .microphoneId(scene_character.getMicrophone().getMicrophoneId())
                                .microphoneName(scene_character.getMicrophone().getMicrophoneName())
                                .comment(scene_character.getComment())
                                .sceneCharacterId(scene_character.getScene_character_id())
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
                                .comment(scene_character.getComment())
                                .sceneCharacterId(scene_character.getScene_character_id())
                                //.microphoneId(scene_character.getMicrophone().getMicrophoneId())
                                //.microphoneName(scene_character.getMicrophone().getMicrophoneName())
                                .build();
                        customList.add(actorOwnSceneCustom);
                    }
                }
            }
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

    @Transactional
    public String comment(Long id, String comment) {
        Scene_character scene_character = scene_characterRepository.findById(id).orElse(null);
        if(comment != null) {
            scene_character.setComment(comment);
        }
        else{
            scene_character.setComment("");
        }
        scene_characterRepository.save(scene_character);

        return comment;
    }
}
