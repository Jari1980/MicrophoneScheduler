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
        try {
            personage.setActor(actorRepository.findById(actorId).orElseThrow());
        } catch (Exception e) {
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
        for (Personage personage : personages) {
            List<Scene> customScenes = new ArrayList<>();

            for (Scene_character scene_character : personage.getScene_characters()) {
                Scene customScene = Scene.builder()
                        .sceneId(scene_character.getScene().getSceneId())
                        .actNumber(scene_character.getScene().getActNumber())
                        .sceneNumber(scene_character.getScene().getSceneNumber())
                        .sceneName(scene_character.getScene().getSceneName())
                        .build();
                customScenes.add(customScene);
            }
            if (scene_characterRepository.existsScene_charactersByPersonage(personage)) {
                PersonageCustom customPerson = PersonageCustom.builder()
                        .personageId(personage.getPersonageId())
                        .personageName(personage.getPersonageName())
                        .playName(personage.getScene_characters().get(0).getScene().getPlay().getPlayName())
                        .actorName(actorRepository.findById(personage.getActor().getActorId()).orElseThrow().getUser().getUsername())
                        .scenes(customScenes)
                        .build();
                customList.add(customPerson);
            } else {
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

        for (PersonageCustom personageCustom : allPersonagesinDb.getPersonages()) {
            if (personageCustom.getPlayName() != null && personageCustom.getPlayName().equals(playName)) {
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

        for (PersonageCustom personageCustom : allPersonagesinDb.getPersonages()) {
            for (Scene scene : personageCustom.getScenes()) {
                if (scene.getSceneName() != null && scene.getSceneId() == sceneId) {
                    customList.add(personageCustom);
                }
            }
        }
        personageInDbCustomDTO.setPersonages(customList);

        return personageInDbCustomDTO;
    }

    public MicrophoneScheduleSuggestedDTO suggestMicrophoneSchedule(String playName) {

        List<Scene> allScenesInPlay = sceneRepository.findAllByPlay(playRepository.getReferenceById(playName));

        List<Scene_character> scene_charactersInPlay = new ArrayList<>();
        for (Scene scene : allScenesInPlay) {
            for (Scene_character scene_character : scene.getScene_characters()) {
                scene_charactersInPlay.add(scene_character);
            }
        }

        List<ScenePersonageMicrophoneCustom> scenePersonageMicrophoneCustoms = new ArrayList<>();

        List<Integer> personageIds = scene_charactersInPlay.stream().map(p -> p.getPersonage().getPersonageId()).toList();
        List<Integer> distictPersonges = personageIds.stream().distinct().toList();
        int maxNeededMicrophonesInPlay = distictPersonges.size(); //When testing with larger Play's if this is exceeded all actors should have their own microphone.

        List<Integer> actNumbers = scene_charactersInPlay.stream().map(s -> s.getScene().getActNumber()).toList();
        List<Integer> distinctActNumbers = actNumbers.stream().distinct().toList();

        List<ScenePersonageMicrophoneCustom> customList = new ArrayList<>();

        for (Scene_character scene_character : scene_charactersInPlay) {
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
        int counter = 0;
        int sceneNumber = 1;
        Dictionary<String, Integer> userMicrophones = new Hashtable<>();
        int microphoneCounter = 1;

        for (ScenePersonageMicrophoneCustom scenePersonageMicrophoneCustom : customList) {

            if (scenePersonageMicrophoneCustom.getActNumber() == 1 && counter == 0) { //Very first element
                customList.get(counter).setMicrophoneId(microphoneCounter);
                userMicrophones.put(scenePersonageMicrophoneCustom.getUserName(), microphoneCounter);
                microphoneCounter++;
                counter++;
            } else {
                String currentUser = customList.get(counter).getUserName();
                int currentScene = customList.get(counter).getSceneNumber();
                int currentAct = customList.get(counter).getActNumber();
                sceneNumber = 1;
                while (true) {
                    if (currentAct == 1 && currentScene == 1) {
                        customList.get(counter).setMicrophoneId(customList.get(counter).getMicrophoneId());
                        userMicrophones.put(scenePersonageMicrophoneCustom.getUserName(), microphoneCounter);
                        microphoneCounter++;
                        counter++;
                        break;
                    } else {
                        int indexOfUser = 0;
                        //Checking if user was same as in previous scenes
                        int finalCounter = counter;
                        int amountScenes = customList.stream().filter(sc ->
                                        sc.getSceneNumber() == customList.get(finalCounter - 1).getSceneNumber() &&
                                                sc.getActNumber() == customList.get(finalCounter - 1).getActNumber())
                                .collect(Collectors.toList()).size();
                        boolean sameUser = false;
                        for (int i = 0; i < amountScenes; i++) {
                            if (currentUser.equals(customList.get(counter - i - 1).getUserName())) {
                                sameUser = true;
                                indexOfUser = counter - i - 1;
                            }
                        }
                        if (sameUser) {
                            customList.get(counter).setMicrophoneId(customList.get(indexOfUser).getMicrophoneId());
                            userMicrophones.put(scenePersonageMicrophoneCustom.getUserName(), indexOfUser);
                            counter++;
                            break;
                        } else {
                            customList.get(counter).setMicrophoneId(microphoneCounter);
                            userMicrophones.put(scenePersonageMicrophoneCustom.getUserName(), counter);
                            microphoneCounter++;
                            counter++;
                            break;
                        }
                    }
                }
                sceneNumber++;
            }
        }

        microphoneScheduleSuggestedDTO.setMicrophoneList(customList);
        return microphoneScheduleSuggestedDTO;
    }
}
