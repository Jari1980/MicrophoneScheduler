package org.workshop.microphoneschedulerapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.dto.MicrophonesInSceneDTOForm;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.repository.PersonageRepository;
import org.workshop.microphoneschedulerapi.repository.PlayRepository;
import org.workshop.microphoneschedulerapi.repository.SceneRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SceneService {

    private SceneRepository sceneRepository;
    private PlayRepository playRepository;
    private PersonageRepository personageRepository;

    @Autowired
    public SceneService(SceneRepository sceneRepository, PlayRepository playRepository, PersonageRepository personageRepository) {
        this.sceneRepository = sceneRepository;
        this.playRepository = playRepository;
        this.personageRepository = personageRepository;
    }

    public List<Scene> getAllScenes(String title) {
        return sceneRepository.findScenesByPlay(playRepository.findById(title).orElseThrow()).orElseThrow();
    }

    public List<Microphone> getAllMicrophones(int sceneId) {
        List<Personage> tempList = sceneRepository.getCharactersBySceneId(sceneId);
        if (tempList.isEmpty()) {
            return null;
        }

        List <Microphone> microphones = new ArrayList<>();
        for (Personage personage : tempList) {
            microphones.add(personageRepository.findById(personage.getPersonageId()).orElseThrow().getMicrophoneId());
        }
        return microphones;
    }
}
