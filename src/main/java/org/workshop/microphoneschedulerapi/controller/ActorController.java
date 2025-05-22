package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshop.microphoneschedulerapi.domain.dto.ActorOwnSceneCustomListDTO;
import org.workshop.microphoneschedulerapi.domain.entity.User;
import org.workshop.microphoneschedulerapi.repository.UserRepository;
import org.workshop.microphoneschedulerapi.service.ActorService;

import java.util.Optional;

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
            String login =
                    SecurityContextHolder.getContext().getAuthentication().getName();
            if (login != null && !login.equals("anonymousUser")) {
                Optional<User> optionalUsers = userRepository.findByUserName(login);
                //return optionalUsers.orElse(null);
                ActorOwnSceneCustomListDTO actorOwnSceneCustomListDTO = actorService.getActorOwnSceneCustomListDTO(optionalUsers.orElseThrow(), playName);

                return new ResponseEntity<>(actorOwnSceneCustomListDTO, HttpStatus.OK);
            }
            return null;//ResponseEntity.ok(actorOwnSceneCustomListDTO);
        } catch (Exception e) {
            return null;//ResponseEntity.badRequest().build();
        }

    }

}
