package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshop.microphoneschedulerapi.domain.dto.ActorOwnSceneCustomListDTO;
import org.workshop.microphoneschedulerapi.domain.dto.CustomUser;
import org.workshop.microphoneschedulerapi.domain.entity.User;
import org.workshop.microphoneschedulerapi.repository.UserRepository;
import org.workshop.microphoneschedulerapi.service.ActorService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "", allowPrivateNetwork = "")
@RequestMapping("/api/v1/actor")
@RestController
public class ActorController {

    private ActorService actorService;
    private UserRepository userRepository;

    @Autowired
    public ActorController(ActorService actorService, UserRepository userRepository) {
        this.actorService = actorService;
        this.userRepository = userRepository;
    }

    @GetMapping("/actorScenes")
    public ResponseEntity<ActorOwnSceneCustomListDTO> getActorScenes(@PathParam("playName") String playName) {
        try{
            String login = SecurityContextHolder.getContext().getAuthentication().getName();

            if (login != null && !login.equals("anonymousUser")) {
                Optional<User> loggedUser = userRepository.findByUserName(login);
                ActorOwnSceneCustomListDTO actorOwnSceneCustomListDTO = actorService.getActorOwnSceneCustomListDTO(loggedUser.orElseThrow(), playName);

                return new ResponseEntity<>(actorOwnSceneCustomListDTO, HttpStatus.OK);
            }
            return null;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<CustomUser>> getUsers() {
        try{
            List<CustomUser> customUsers = actorService.getAllUsers();
            return new ResponseEntity<>(customUsers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
