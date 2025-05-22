package org.workshop.microphoneschedulerapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.workshop.microphoneschedulerapi.configuration.JwtUtil;
import org.workshop.microphoneschedulerapi.domain.entity.*;
import org.workshop.microphoneschedulerapi.domain.model.UserRole;
import org.workshop.microphoneschedulerapi.encoder.CustomPasswordEncoder;
import org.workshop.microphoneschedulerapi.repository.*;
import org.workshop.microphoneschedulerapi.service.CustomUserDetailService;

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
    private JwtUtil jwtUtil;
    private PasswordEncoder customPasswordEncoder;
    private Scene_characterRepository scene_characterRepository;

    @Autowired
    public PlayGround(ActorRepository actorRepository, MicrophoneRepository microphoneRepository,
                      PersonageRepository personageRepository, PlayRepository playRepository,
                      SceneRepository sceneRepository, UserRepository userRepository, JwtUtil jwtUtil,
                      PasswordEncoder customPasswordEncoder, Scene_characterRepository scene_characterRepository) {
        this.actorRepository = actorRepository;
        this.microphoneRepository = microphoneRepository;
        this.personageRepository = personageRepository;
        this.playRepository = playRepository;
        this.sceneRepository = sceneRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.customPasswordEncoder = customPasswordEncoder;
        this.scene_characterRepository = scene_characterRepository;
    }

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {


        Microphone microphone1 = Microphone.builder()
                .microphoneName("Very first Microphone")
                .build();
        microphoneRepository.save(microphone1);

        Microphone microphone2 = new Microphone();
        microphone2.setMicrophoneName("Second Microphone");
        microphoneRepository.save(microphone2);

        //Ok, microphones created with and without builder


        User user1 = User.builder()
                .userName("user1")
                .password(customPasswordEncoder.encode("1234"))
                .userRole(UserRole.ACTOR)
                .build();
        userRepository.save(user1);


        User user2 = new User();
        user2.setUserName("user2");
        user2.setPassword(customPasswordEncoder.encode("2222"));
        user2.setUserRole(UserRole.ADMINISTRATOR);
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
                .personageName("JulTomte")
                .actor(actor1)
                //.microphoneId(microphone1)
                .build();
        personageRepository.save(personage1);

        Personage personage2 = new Personage();
        personage2.setPersonageName("SnusMonster");
        personage2.setActor(actor2);
        //personage2.setMicrophoneId(microphone2);
        personageRepository.save(personage2);

        Personage personage3 = Personage.builder()
                .personageName("TestPersonage")
                .actor(actor1)
                //.microphoneId(microphone1)
                .build();
        personageRepository.save(personage3);

        Personage personage4 = Personage.builder()
                .personageName("KaffeTomte")
                .actor(actor1)
                //.microphoneId(microphone1)
                .build();
        personageRepository.save(personage4);


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
                //.characters(charactersScene1)
                .build();
        sceneRepository.save(scene1);

        Scene scene2 = Scene.builder()
                .play(play1)
                .actNumber(1)
                .sceneNumber(2)
                .sceneName("Test scene 2")
                .build();
        sceneRepository.save(scene2);

        Scene scene3 = Scene.builder()
                .play(play1)
                .actNumber(1)
                .sceneNumber(3)
                .sceneName("Test scene 3")
                .build();
        sceneRepository.save(scene3);

        Scene scene4 = Scene.builder()
                .play(play1)
                .actNumber(2)
                .sceneNumber(1)
                .sceneName("Test scene 4")
                .build();
        sceneRepository.save(scene4);

        Scene scene5 = Scene.builder()
                .play(play1)
                .actNumber(2)
                .sceneNumber(2)
                .sceneName("Test scene 5")
                .build();
        sceneRepository.save(scene5);

        System.out.println("-----------------------");
        /*
        System.out.println("Looking for all characters in scene1:");
        List<Personage> characters = sceneRepository.findById(1).orElseThrow().getCharacters();
        for (Personage character : characters) {
            System.out.println(character.getMicrophoneId());
        }
         */
        System.out.println("------------------------");
        System.out.println("---------Checking encoded password-----------");
        System.out.println(userRepository.findByUserName("user1").orElseThrow().getPassword());
        System.out.println("---------Checking if password matches user1:-----------");
        System.out.println(passwordEncoder.matches("1234", (userRepository.findByUserName("user1").orElseThrow().getPassword())));
        System.out.println("----------Checking false----------------------");
        System.out.println(passwordEncoder.matches("2222", (userRepository.findByUserName("user1").orElseThrow().getPassword())));

        //Looks ok, next securing the password

        String token = jwtUtil.generateToken("Test token");
        System.out.println(token);

        Scene_character test = Scene_character.builder()
                .scene(scene1)
                .personage(personage1)
                //.microphone(microphone1)
                .build();
        scene_characterRepository.save(test);

        Scene_character test2 = Scene_character.builder()
                .scene(scene2)
                .personage(personage1)
                //.microphone(microphone1)
                .build();
        scene_characterRepository.save(test2);

        Scene_character test3 = Scene_character.builder()
                .scene(scene2)
                .personage(personage2)
                //.microphone(microphone2)
                .build();
        scene_characterRepository.save(test3);

        Scene_character test4 = Scene_character.builder()
                .scene(scene3)
                .personage(personage1)
                //.microphone(microphone1)
                .build();
        scene_characterRepository.save(test4);

        Scene_character test5 = Scene_character.builder()
                .scene(scene4)
                .personage(personage1)
                //.microphone(microphone1)
                .build();
        scene_characterRepository.save(test4);

    }
}
