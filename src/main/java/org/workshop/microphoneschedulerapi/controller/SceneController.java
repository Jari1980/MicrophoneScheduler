package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/play{title}")
    public ResponseEntity<List<Scene>> getPlay(@PathParam("title") String title) {
        List<Scene> scenes = sceneService.getAllScenes(title);

        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
