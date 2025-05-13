package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.configuration.JwtUtil;
import org.workshop.microphoneschedulerapi.domain.entity.User;
import org.workshop.microphoneschedulerapi.repository.UserRepository;
import org.workshop.microphoneschedulerapi.service.CustomUserDetailService;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailService customUserDetailService;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                          CustomUserDetailService customUserDetailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.existsByUserName(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User newUser = User.builder()
                .userName(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(jwtUtil.generateToken(userDetails.getUsername()));
    }

    //In order to reach this endpoint user needs to be authenticated with bearer token (Jwt token) working
    @GetMapping("/loggedTest")
    public ResponseEntity<String> loggTest() {
        return ResponseEntity.ok("Test logged");
    }

    //More testing, working with token
    @GetMapping("/userInfo")
    public ResponseEntity<UserDetails> userInfo(@PathParam("userName") String userName) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
        return ResponseEntity.ok(userDetails);
    }

}
