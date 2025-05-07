package org.workshop.microphoneschedulerapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.repository.PlayRepository;
import org.workshop.microphoneschedulerapi.repository.SceneRepository;

import java.util.List;

@Service
public class SceneService {

    private SceneRepository sceneRepository;
    private PlayRepository playRepository;

    @Autowired
    public SceneService(SceneRepository sceneRepository, PlayRepository playRepository) {
        this.sceneRepository = sceneRepository;
        this.playRepository = playRepository;
    }

    public List<Scene> getAllScenes(String title) {
        return sceneRepository.findScenesByPlay(playRepository.findById(title).orElseThrow()).orElseThrow();
    }
}
