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
import org.workshop.microphoneschedulerapi.repository.ActorRepository;
import org.workshop.microphoneschedulerapi.repository.PersonageRepository;
import org.workshop.microphoneschedulerapi.repository.PlayRepository;
import org.workshop.microphoneschedulerapi.repository.SceneRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private PlayRepository playRepository;
    private PersonageRepository personageRepository;
    private SceneRepository sceneRepository;
    private ActorRepository actorRepository;


    @Autowired
    public AdminService(PlayRepository playRepository, PersonageRepository personageRepository, SceneRepository sceneRepository, ActorRepository actorRepository) {
        this.playRepository = playRepository;
        this.personageRepository = personageRepository;
        this.sceneRepository = sceneRepository;
        this.actorRepository = actorRepository;
    }

    public List<Play> getAllPlays() {
        return playRepository.findAll();
    }

    public Play createPlay(Play play) {
        return playRepository.save(play);
    }

    public void deletePlay(String playName) {
        List<Scene> scenesToBeRemoved = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));
        for (Scene scene : scenesToBeRemoved) {
            personageRepository.deleteAll(sceneRepository.getCharactersBySceneId(scene.getSceneId()));
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
        List<Personage> characters = scene.getCharacters();
        characters.add(personageRepository.findById(personageId).orElseThrow());
        scene.setCharacters(characters);
    }

    @Transactional
    public void removePersonageFromScene(int sceneId, int personageId) {
        Scene scene = sceneRepository.findSceneBySceneId(sceneId);
        List<Personage> characters = scene.getCharacters();
        characters.remove(personageRepository.findById(personageId).orElseThrow());
        scene.setCharacters(characters);
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
}
