package org.workshop.microphoneschedulerapi.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.dto.*;
import org.workshop.microphoneschedulerapi.domain.entity.*;
import org.workshop.microphoneschedulerapi.domain.model.PersonageCustom;
import org.workshop.microphoneschedulerapi.domain.model.ScenePersonageMicrophoneCustom;
import org.workshop.microphoneschedulerapi.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
            List<Scene> customScenes = new ArrayList<>();

            for(Scene_character scene_character : personage.getScene_characters()) {
                Scene customScene = Scene.builder()
                        .sceneId(scene_character.getScene().getSceneId())
                        .actNumber(scene_character.getScene().getActNumber())
                        .sceneNumber(scene_character.getScene().getSceneNumber())
                        .sceneName(scene_character.getScene().getSceneName())
                        .build();
                customScenes.add(customScene);
            }
            if(scene_characterRepository.existsScene_charactersByPersonage(personage)) {
            PersonageCustom customPerson = PersonageCustom.builder()
                    .personageId(personage.getPersonageId())
                    .personageName(personage.getPersonageName())
                    .playName(personage.getScene_characters().get(0).getScene().getPlay().getPlayName())
                    .actorName(actorRepository.findById(personage.getActor().getActorId()).orElseThrow().getUser().getUsername())
                    .scenes(customScenes)
                    .build();
            customList.add(customPerson);
            }
            else{
                PersonageCustom customPerson = PersonageCustom.builder()
                        .personageId(personage.getPersonageId())
                        .personageName(personage.getPersonageName())
                        .actorName(actorRepository.findById(personage.getActor().getActorId()).orElseThrow().getUser().getUsername())
                        .scenes(customScenes)
                        .build();
                customList.add(customPerson);
            }
        }
        personageInDbCustomDTO.setPersonages(customList);

        return personageInDbCustomDTO;
    }

    public PersonageInDbCustomDTO getAllPersonagesInPlay(String playName) {
        PersonageInDbCustomDTO personageInDbCustomDTO = new PersonageInDbCustomDTO();
        List<PersonageCustom> customList = new ArrayList<>();
        PersonageInDbCustomDTO allPersonagesinDb = getAllPersonages();

        for(PersonageCustom personageCustom : allPersonagesinDb.getPersonages()) {
            if(personageCustom.getPlayName() != null && personageCustom.getPlayName().equals(playName)) {
                customList.add(personageCustom);
            }
        }
        personageInDbCustomDTO.setPersonages(customList);

        return personageInDbCustomDTO;
    }

    public PersonageInDbCustomDTO getAllPersonagesInScene(int sceneId) {
        PersonageInDbCustomDTO personageInDbCustomDTO = new PersonageInDbCustomDTO();
        List<PersonageCustom> customList = new ArrayList<>();
        PersonageInDbCustomDTO allPersonagesinDb = getAllPersonages();

        for(PersonageCustom personageCustom : allPersonagesinDb.getPersonages()) {
            for(Scene scene : personageCustom.getScenes()) {
                if(scene.getSceneName() != null && scene.getSceneId() == sceneId) {
                    customList.add(personageCustom);
                }
            }
        }
        personageInDbCustomDTO.setPersonages(customList);

        return personageInDbCustomDTO;
    }

    public MicrophoneScheduleSuggestedDTO suggestMicrophoneSchedule(String playName){

        List<Scene> allScenesInPlay = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));

        List<Scene_character> scene_charactersInPlay = new ArrayList<>();
        for(Scene scene : allScenesInPlay) {
            for(Scene_character scene_character : scene.getScene_characters()) {
                scene_charactersInPlay.add(scene_character);
            }
        }

        List<ScenePersonageMicrophoneCustom> scenePersonageMicrophoneCustoms = new ArrayList<>();

        List<Integer> personageIds = scene_charactersInPlay.stream().map(p -> p.getPersonage().getPersonageId()).toList();
        List<Integer> distictPersonges = personageIds.stream().distinct().toList();
        int maxNeededMicrophonesInPlay = distictPersonges.size();

        List<Integer> actNumbers = scene_charactersInPlay.stream().map(s -> s.getScene().getActNumber()).toList();
        List<Integer> distinctActNumbers = actNumbers.stream().distinct().toList();

        List<ScenePersonageMicrophoneCustom> customList = new ArrayList<>();

        for(Scene_character scene_character : scene_charactersInPlay) {
                ScenePersonageMicrophoneCustom scenePersonageMicrophoneCustom = ScenePersonageMicrophoneCustom.builder()
                        .userId(scene_character.getPersonage().getActor().getUser().getUserId())
                        .userName(scene_character.getPersonage().getActor().getUser().getUsername())
                        .sceneId(scene_character.getScene().getSceneId())
                        .sceneNumber(scene_character.getScene().getSceneNumber())
                        .sceneName(scene_character.getScene().getSceneName())
                        .actNumber(scene_character.getScene().getActNumber())
                        .personageName(scene_character.getPersonage().getPersonageName())
                        .build();
                customList.add(scenePersonageMicrophoneCustom);
        }

        customList.sort(Comparator.comparing(ScenePersonageMicrophoneCustom::getActNumber));


        List<Microphone> suggestedMicrophones = new ArrayList<>();
        MicrophoneScheduleSuggestedDTO microphoneScheduleSuggestedDTO = new MicrophoneScheduleSuggestedDTO();
        int previousSceneNumber = 1;
        //int previousActNumber = 1;
        int actIndex = 1;
        int actsInPlay = distinctActNumbers.size();
        int customListLength = customList.size();
        int counter = 0;
        int sceneInActCounter = 1;
        int sceneNumber = 1;
        //int lastMicrophone = 0;
        //List<Integer> lastMicrophone = new ArrayList<>();
        Dictionary<String, Integer> lastMicrophone = new Hashtable<>();


        //List<ScenePersonageMicrophoneCustom> customListWithMicrophones = new ArrayList<>();
        for(ScenePersonageMicrophoneCustom scenePersonageMicrophoneCustom : customList) {
            if(scenePersonageMicrophoneCustom.getActNumber() == 1) {
                if(counter == 0){ //Very first element
                    customList.get(counter).setMicrophoneId(counter + 1);
                    lastMicrophone.put(scenePersonageMicrophoneCustom.getUserName(), counter);
                }
                else{
                    sceneNumber = 1;
                    while(true){
                        //if((counter - sceneNumber >= 0) && customList.get(counter).getSceneNumber() != customList.get(counter-sceneNumber).getSceneNumber()){
                        //    sceneNumber++;

                        //}
                        if(counter - sceneNumber < 0){
                            customList.get(counter).setMicrophoneId(lastMicrophone.get(scenePersonageMicrophoneCustom.getUserName()));
                            break;
                        }
                        if(sceneNumber > 10){
                            customList.get(counter).setMicrophoneId(counter + 1);
                            lastMicrophone.put(scenePersonageMicrophoneCustom.getUserName(), counter);
                            sceneNumber++;
                            break;
                        }
                        sceneNumber++;
                    }
                }

                if(customListLength > counter){
                    counter++;
                    sceneInActCounter++;
                    if(scenePersonageMicrophoneCustom.getActNumber() == actIndex + 1) {
                        actIndex++;
                        sceneNumber = 1;
                    }
                }
            }
            //New act
            else {
                sceneNumber = 1;
                while (true) {
                    if((counter - sceneNumber >= 0) && customList.get(counter).getSceneNumber() != customList.get(counter-sceneNumber).getSceneNumber()){
                        sceneNumber++;

                    } else {
                        customList.get(counter).setMicrophoneId(counter + 1);
                        sceneNumber++;
                        break;
                    }
                    if (sceneNumber == customList.get(counter).getSceneNumber()) {
                        customList.get(counter).setMicrophoneId(customList.get(counter).getMicrophoneId());
                        break;
                    }
                }
                if(customListLength > counter){
                    counter++;
                    sceneInActCounter++;
                    if(scenePersonageMicrophoneCustom.getActNumber() == actIndex + 1) {
                        actIndex++;
                        sceneNumber = 1;
                    }
                }
            }

        }

        /*
        for(int i = 0; i < scene_charactersInPlay.size(); i++) {
            if(suggestedMicrophones.isEmpty()){
                Microphone microphone = Microphone.builder()
                        .microphoneName("Microphone " + i + 1)
                        .build();
                suggestedMicrophones.add(microphone);
                ScenePersonageMicrophoneCustom scenePersonageMicrophoneCustom = ScenePersonageMicrophoneCustom.builder()
                        .microphoneId(microphone.getMicrophoneId())
                        .userId(scene_charactersInPlay.get(i).getPersonage().getActor().getUser().getUserId())
                        .userName(scene_charactersInPlay.get(i).getPersonage().getActor().getUser().getUsername())
                        .sceneId(scene_charactersInPlay.get(i).getScene().getSceneId())
                        .sceneNumber(scene_charactersInPlay.get(i).getScene().getSceneNumber())
                        .sceneName(scene_charactersInPlay.get(i).getScene().getSceneName())
                        .actNumber(scene_charactersInPlay.get(i).getScene().getActNumber())
                        .personageName(scene_charactersInPlay.get(i).getPersonage().getPersonageName())
                        .build();
                microphoneScheduleSuggestedDTO.getMicrophoneList().add(scenePersonageMicrophoneCustom);
            }
            else{
                if(scene_charactersInPlay.get(i).getScene().getSceneNumber() == scene_charactersInPlay.get(i - 1).getScene().getSceneNumber()){ //We are in same scene

                }
            }
        }

         */

        return null;
    }

}
