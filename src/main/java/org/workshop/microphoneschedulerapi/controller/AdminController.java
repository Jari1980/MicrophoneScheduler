package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.domain.dto.SceneCustomListDTO;
import org.workshop.microphoneschedulerapi.domain.entity.Play;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.service.AdminService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/listAllPlay")
    public ResponseEntity<List<Play>> listAllPlay() {
        List<Play> plays = adminService.getAllPlays();

        return ResponseEntity.ok(plays);
    }

    @PostMapping("/createPlay")
    public ResponseEntity<Play> createPlay(@RequestBody Play play) {
        Play created = adminService.createPlay(play);

        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/deletePlay")
    public ResponseEntity<Void> deletePlay(@PathParam("playName") String playName) {
        adminService.deletePlay(playName);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/updatePlay")
    public ResponseEntity<Void> updatePlay(@RequestBody Play play) {
        adminService.updatePlay(play.getPlayName(), play.getDateCreated(), play.getDescription());

        return ResponseEntity.ok().build();
    }


    @GetMapping("/allScenesInPlay")
    public ResponseEntity<SceneCustomListDTO> allScenesInPlay(@PathParam("playName") String playName) {
        SceneCustomListDTO customSceneList = adminService.getAllScenesInPlay(playName);

        return ResponseEntity.ok(customSceneList);
    }




}
