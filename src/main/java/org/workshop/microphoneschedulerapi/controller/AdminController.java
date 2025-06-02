package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.domain.dto.*;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.entity.Play;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;
import org.workshop.microphoneschedulerapi.service.AdminService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "", allowPrivateNetwork = "")
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
        try {
            Play created = adminService.createPlay(play);
            return ResponseEntity.ok(created);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletePlay")
    public ResponseEntity<Void> deletePlay(@PathParam("playName") String playName) {
        try {
            adminService.deletePlay(playName);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updatePlay")
    public ResponseEntity<Void> updatePlay(@RequestBody Play play) {
        try {
            adminService.updatePlay(play.getPlayName(), play.getDateCreated(), play.getDescription());
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/allScenesInPlay")
    public ResponseEntity<SceneCustomListDTO> allScenesInPlay(@PathParam("playName") String playName) {
        try {
            SceneCustomListDTO customSceneList = adminService.getAllScenesInPlay(playName);
            return ResponseEntity.ok(customSceneList);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createScene")
    public ResponseEntity<Void> createScene(@RequestBody CreateSceneDTOForm form) {
        try{
            adminService.createScene(form);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/editScene")
    public ResponseEntity<Void> editScene(@RequestBody UpdateSceneDTOForm form) {
        try{
            adminService.updateScene(form);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteScene")
    public ResponseEntity<Void> deleteScene(@PathParam("sceneId") int sceneId) {
        try{
            adminService.deleteScene(sceneId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
          return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/addPersonageToScene")
    public ResponseEntity<Void> addPersonageToScene(@RequestBody ManagePersonageToScene form){
        try{
            adminService.addPersonageToScene(form.sceneId(), form.personageId());
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/removePersonageFromScene")
    public ResponseEntity<Void> removePersonageFromScene(@RequestBody ManagePersonageToScene form){
        try{
            adminService.removePersonageFromScene(form.sceneId(), form.personageId());
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/assignActorToPersonage")
    public ResponseEntity<Void> assignActorToPersonage(@RequestBody ManageActorToPersonage form){
        try{
            adminService.assignActorToPersonage(form.actorId(), form.personageId());
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createPersonage")
    public ResponseEntity<Void>  createPersonage(@RequestBody CreatePersonageDTOForm form){
        try{
            adminService.createPersonage(form);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("getAllPersonageInDb")
    public ResponseEntity<PersonageInDbCustomDTO> getAllPersonageInDb() {
        try{
            PersonageInDbCustomDTO personages = adminService.getAllPersonages();
            return ResponseEntity.ok(personages);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllPersonageInPlay")
    public ResponseEntity<PersonageInDbCustomDTO> getAllPersonageInPlay(@PathParam("playName") String playName) {
        try{
            PersonageInDbCustomDTO personages = adminService.getAllPersonagesInPlay(playName);
            return ResponseEntity.ok(personages);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllPersonageInScene")
    public ResponseEntity<PersonageInDbCustomDTO> getAllPersonageInScene(@PathParam("sceneId") int sceneId) {
        try{
            PersonageInDbCustomDTO personages = adminService.getAllPersonagesInScene(sceneId);
            return ResponseEntity.ok(personages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/suggestMicrophoneSchedule")
    public ResponseEntity<MicrophoneScheduleSuggestedDTO> suggestMicrophoneSchedule(@PathParam("playName") String playName){
        try{
            MicrophoneScheduleSuggestedDTO microphoneScheduleSuggestedDTO = adminService.suggestMicrophoneSchedule(playName);

            return ResponseEntity.ok(microphoneScheduleSuggestedDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getusersandroles")
    public ResponseEntity<List<UsersAndRolesDTO>> getUsersAndRoles(){
        try{
            List<UsersAndRolesDTO> usersAndRoles = adminService.getUsersAndRoles();
            return ResponseEntity.ok(usersAndRoles);
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/setuserrole")
    public ResponseEntity<Void> setUserRole(@RequestBody SetUserRoleDTOForm form){
        try{
            adminService.setUserRole(form.userId(), form.userRole());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<Void> deleteUser(@PathParam("userId") Long userId){
        try{
            adminService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
