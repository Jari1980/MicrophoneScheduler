package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.configuration.JwtUtil;
import org.workshop.microphoneschedulerapi.domain.dto.UserLoginDTO;
import org.workshop.microphoneschedulerapi.domain.entity.User;
import org.workshop.microphoneschedulerapi.domain.model.UserRole;
import org.workshop.microphoneschedulerapi.repository.UserRepository;
import org.workshop.microphoneschedulerapi.service.CustomUserDetailService;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "", allowPrivateNetwork = "")
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
                .userRole(UserRole.ACTOR)
                .build();
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }


    /**
     * Login method authenticates the user with given credentials, if authenticated the method will return a valid
     * Jwt token, userName and userRole. If authentication fails the method will return unauthorized.
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserLoginDTO loginData = UserLoginDTO.builder()
                .jwtToken(jwtUtil.generateToken(userDetails.getUsername()))
                .userName(authentication.getName())
                .userRole(authentication.getAuthorities().iterator().next().getAuthority())
                .build();
        return ResponseEntity.ok(loginData);
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
