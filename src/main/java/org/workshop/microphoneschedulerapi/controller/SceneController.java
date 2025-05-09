package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.domain.dto.MicrophonesInSceneDTOForm;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.service.SceneService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/project")
@RestController
public class SceneController {

    SceneService sceneService;

    @Autowired
    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/completePlay{title}")
    public ResponseEntity<List<Scene>> getCompletePlay(@PathParam("title") String title) {
        List<Scene> scenes = sceneService.getAllScenes(title);

        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

    @GetMapping("/microphonesInScene{id}")
    public ResponseEntity<List<Microphone>> getMicrophonesInScene(@PathParam("sceneId") int sceneId) {
        try{
            List<Microphone> microphones = sceneService.getAllMicrophones(sceneId);
            return new ResponseEntity<>(microphones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
