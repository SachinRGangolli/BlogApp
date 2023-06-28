package com.blogApp.controller;

import com.blogApp.entity.Role;
import com.blogApp.entity.User;
import com.blogApp.payload.SignInDto;
import com.blogApp.payload.SignUpDto;
import com.blogApp.repository.RoleRepository;
import com.blogApp.repository.UserRepository;
import org.modelmapper.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDto signInDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getUserNameOrEmail(),
                        signInDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
       if (userRepository.existsByUserName(signUpDto.getUserName())){
           return new ResponseEntity<>("User Name already exist", HttpStatus.BAD_REQUEST);
       }
       if (userRepository.existsByEmail(signUpDto.getEmail())){
           return new ResponseEntity<>("email already exists",HttpStatus.BAD_REQUEST);
       }

       User user=new User();
       user.setName(signUpDto.getName());
       user.setUserName(signUpDto.getUserName());
       user.setEmail(signUpDto.getEmail());
       user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
