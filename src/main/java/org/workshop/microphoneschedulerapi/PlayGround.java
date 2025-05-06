package org.workshop.microphoneschedulerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.workshop.microphoneschedulerapi.domain.entity.*;
import org.workshop.microphoneschedulerapi.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayGround implements CommandLineRunner {

    private ActorRepository actorRepository;
    private MicrophoneRepository microphoneRepository;
    private PersonageRepository personageRepository;
    private PlayRepository playRepository;
    private SceneRepository sceneRepository;
    private UserRepository userRepository;

    @Autowired
    public PlayGround(ActorRepository actorRepository, MicrophoneRepository microphoneRepository,
                      PersonageRepository personageRepository, PlayRepository playRepository,
                      SceneRepository sceneRepository, UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.microphoneRepository = microphoneRepository;
        this.personageRepository = personageRepository;
        this.playRepository = playRepository;
        this.sceneRepository = sceneRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Microphone microphone1 = Microphone.builder()
                .name("Very first Microphone")
                .build();
        microphoneRepository.save(microphone1);

        Microphone microphone2 = new Microphone();
        microphone2.setName("Second Microphone");
        microphoneRepository.save(microphone2);

        //Ok, microphones created with and without builder


        User user1 = User.builder()
                .userName("user1")
                .password("1234")
                .role("Super JAVA user")
                .build();
        userRepository.save(user1);

        User user2 = new User();
        user2.setUserName("user2");
        user2.setPassword("2222");
        user2.setRole("Super Maccintosh user");
        userRepository.save(user2);

        //Ok

        Actor actor1 = Actor.builder()
                .user(user1)
                .build();
        actorRepository.save(actor1);

        Actor actor2 = new Actor();
        actor2.setUser(user2);
        actorRepository.save(actor2);

        //Ok

        Personage personage1 = Personage.builder()
                .name("JulTomte")
                .actor(actor1)
                .microphoneId(microphone1)
                .build();
        personageRepository.save(personage1);

        Personage personage2 = new Personage();
        personage2.setName("SnusMonster");
        personage2.setActor(actor2);
        personage2.setMicrophoneId(microphone2);
        personageRepository.save(personage2);


        Play play1 = Play.builder()
                .playName("SnusDrömmar")
                .dateCreated(LocalDate.now())
                .description("Test play")
                .build();
        playRepository.save(play1);

        //Ok


        List<Personage> charactersScene1 = new ArrayList<>();
        charactersScene1.add(personage1);
        charactersScene1.add(personage2);


        Scene scene1 = Scene.builder()
                .play(play1)
                .actNumber(1)
                .sceneNumber(1)
                .sceneName("Test scene 1")
                .characters(charactersScene1)
                .build();
        sceneRepository.save(scene1);
        System.out.println("-----------------------");
        System.out.println("Looking for all characters in scene1:");
        List<Personage> characters = sceneRepository.findById(1).orElseThrow().getCharacters();
        for (Personage character : characters) {
            System.out.println(character.getActor());
        }
        System.out.println("------------------------");

        //Looks ok, next securing the password
    }
}
