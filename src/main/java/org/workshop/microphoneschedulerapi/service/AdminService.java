package org.workshop.microphoneschedulerapi.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.dto.*;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.Play;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.domain.entity.Scene_character;
import org.workshop.microphoneschedulerapi.domain.model.PersonageCustom;
import org.workshop.microphoneschedulerapi.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private PlayRepository playRepository;
    private PersonageRepository personageRepository;
    private SceneRepository sceneRepository;
    private ActorRepository actorRepository;
    private Scene_characterRepository scene_characterRepository;


    @Autowired
    public AdminService(PlayRepository playRepository, PersonageRepository personageRepository,
                        SceneRepository sceneRepository, ActorRepository actorRepository,
                        Scene_characterRepository scene_characterRepository) {
        this.playRepository = playRepository;
        this.personageRepository = personageRepository;
        this.sceneRepository = sceneRepository;
        this.actorRepository = actorRepository;
        this.scene_characterRepository = scene_characterRepository;
    }

    public List<Play> getAllPlays() {
        return playRepository.findAll();
    }

    public Play createPlay(Play play) {
        return playRepository.save(play);
    }


    @Transactional
    public void deletePlay(String playName) {
        List<Scene> scenesToBeRemoved = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));
        for (Scene scene : scenesToBeRemoved) {
            var toBeRemoved = scene.getScene_characters();
            for (Scene_character scene_character : toBeRemoved) {
                personageRepository.delete(scene_character.getPersonage());
                scene_character.setPersonage(null);
                scene_character.setScene(null);
                scene_character.setMicrophone(null);
            }
            scene.setScene_characters(null);
        }
        sceneRepository.deleteAllByPlay(playRepository.getReferenceById(playName));
        playRepository.delete(playRepository.getReferenceById(playName));
    }

    public void updatePlay(String playName, LocalDate date, String description) {
        playRepository.updatePlay(playName, date, description);
    }

    public SceneCustomListDTO getAllScenesInPlay(String playName) {
        List<Scene> scenes = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));
        SceneCustomListDTO sceneCustomListDTO = new SceneCustomListDTO();
        List<Scene> scenesInPlay = new ArrayList<>();
        for (Scene scene : scenes) {
            Scene newScene = Scene.builder()
                    .sceneId(scene.getSceneId())
                    .sceneName(scene.getSceneName())
                    .actNumber(scene.getActNumber())
                    .sceneNumber(scene.getSceneNumber())
                    .build();
            scenesInPlay.add(newScene);
        }
        sceneCustomListDTO.setScenes(scenesInPlay);
        return sceneCustomListDTO;
    }

    public void createScene(CreateSceneDTOForm form) {
        Scene newScene = Scene.builder()
                .play(playRepository.getReferenceById(form.playName()))
                .actNumber(form.actNumber())
                .sceneName(form.sceneName())
                .sceneNumber(form.sceneNumber())
                .build();
        sceneRepository.save(newScene);
    }

    public void updateScene(UpdateSceneDTOForm form) {
        sceneRepository.updateScene(form.sceneId(), form.sceneName(), form.actNumber(), form.sceneNumber());
    }

    public void deleteScene(int sceneId) {
        sceneRepository.deleteById(sceneId);
    }

    @Transactional
    public void addPersonageToScene(int sceneId, int personageId) {
        Scene scene = sceneRepository.findSceneBySceneId(sceneId);
        List<Scene_character> sceneCharacters = scene.getScene_characters();

        Scene_character scene_character = Scene_character.builder()
                .scene(scene)
                .personage(personageRepository.findById(personageId).orElseThrow())
                .build();

        sceneCharacters.add(scene_character);
        scene.setScene_characters(sceneCharacters);
        scene_characterRepository.save(scene_character);

        /*
        Personage personage = personageRepository.findById(personageId).orElseThrow();
        List<Scene_character> sceneCharacters2 = personage.getScene_characters();

        Scene_character scene_character2 = Scene_character.builder()
                .scene(scene)
                .personage(personageRepository.findById(personageId).orElseThrow())
                //.personage(personageRepository.getReferenceById(personageId))
                .build();

        sceneCharacters2.add(scene_character2);
        personage.setScene_characters(sceneCharacters2);
         */

    }

    @Transactional
    public void removePersonageFromScene(int sceneId, int personageId) {
        Scene scene = sceneRepository.findSceneBySceneId(sceneId);
        List<Scene_character> sceneCharacters = scene.getScene_characters();
        Scene_character scene_characterToBeRemoved = sceneCharacters.stream()
                .filter(s -> s.getPersonage().getPersonageId() == personageId)
                        .findFirst().get();

        sceneCharacters.remove(scene_characterToBeRemoved);
        scene_characterRepository.delete(scene_characterToBeRemoved);
    }

    @Transactional
    public void assignActorToPersonage(int actorId, int personageId) {
        Personage personage = personageRepository.findById(personageId).orElseThrow();
        try{
            personage.setActor(actorRepository.findById(actorId).orElseThrow());
        }catch (Exception e){
            personage.setActor(null);
        }
    }

    public void createPersonage(CreatePersonageDTOForm form) {
        Personage newPersonage = Personage.builder()
                .personageName(form.personageName())
                .build();
        personageRepository.save(newPersonage);
    }

    public PersonageInDbCustomDTO getAllPersonages() {
        PersonageInDbCustomDTO personageInDbCustomDTO = new PersonageInDbCustomDTO();
        List<PersonageCustom> customList = new ArrayList<>();
        List<Personage> personages = personageRepository.findAll();
        for(Personage personage : personages) {

            if(scene_characterRepository.existsScene_charactersByPersonage(personage)) {
            PersonageCustom customPerson = PersonageCustom.builder()
                    .personageId(personage.getPersonageId())
                    .personageName(personage.getPersonageName())
                    .playName(personage.getScene_characters().get(0).getScene().getPlay().getPlayName())
                    .actorName(actorRepository.findById(personage.getActor().getActorId()).orElseThrow().getUser().getUsername())
                    .build();
            customList.add(customPerson);
            }
            else{
                PersonageCustom customPerson = PersonageCustom.builder()
                        .personageId(personage.getPersonageId())
                        .personageName(personage.getPersonageName())
                        .actorName(actorRepository.findById(personage.getActor().getActorId()).orElseThrow().getUser().getUsername())
                        .build();
                customList.add(customPerson);
            }


        }
        personageInDbCustomDTO.setPersonages(customList);


        return personageInDbCustomDTO;
    }

    /*
    public PersonageInPlayCustomDTO getAllPersonagesInPlay(String playName) {
        List<Scene> scenes = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));
        PersonageInPlayCustomDTO personageInPlayCustomDTO = new PersonageInPlayCustomDTO();
        List <Personage> personagesOneScene = new ArrayList<>();
        List<Personage> personagesInPlay = new ArrayList<>();
        for (Scene scene : scenes) {
            List<Personage> personagesInScene = scene.getCharacters();
            for (Personage personage : personagesInScene) {
                Personage persona = Personage.builder()
                        .personageId(personage.getPersonageId())
                        .personageName(personage.getPersonageName())
                        .build();
                personagesOneScene.add(persona);
            }
            personagesInPlay.addAll(personagesOneScene);
        }
        personageInPlayCustomDTO.setPersonages(personagesInPlay);
        return personageInPlayCustomDTO;
    }
     */
}
