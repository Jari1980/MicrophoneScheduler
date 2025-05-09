package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.domain.dto.MicrophoneUpdateDTOForm;
import org.workshop.microphoneschedulerapi.domain.dto.MicrophonesInSceneDTOForm;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.service.MicrophoneService;
import org.workshop.microphoneschedulerapi.service.SceneService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/project")
@RestController
public class SceneController {

    SceneService sceneService;
    MicrophoneService microphoneService;

    @Autowired
    public SceneController(SceneService sceneService, MicrophoneService microphoneService) {
        this.sceneService = sceneService;
        this.microphoneService = microphoneService;
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

    //Add following three Microphone functionalities to a new "MicrophoneController"
    @PostMapping("/createMicrophone{microphoneName}")
    public ResponseEntity<Void> createMicrophone(@PathParam("microphoneName") String microphoneName) {
        try{
            microphoneService.createMicrophone(microphoneName);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteMicrophone{microphoneName}")
    public ResponseEntity<Void> deleteMicrophone(@PathParam("microphoneName") String microphoneName) {
        try{
            microphoneService.deleteMicrophone(microphoneName);
            return new ResponseEntity<>(HttpStatus.GONE);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateMicrophone")
    public ResponseEntity<Void> updateMicrophone(@RequestBody MicrophoneUpdateDTOForm form) {
        try{
            microphoneService.updateMicrophone(form.microphoneId(), form.microphoneName());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
