package com.usermanager.api.controller;

import com.usermanager.api.domain.User;
import com.usermanager.api.domain.dto.AuthResponseDTO;
import com.usermanager.api.domain.dto.AuthenticationDTO;
import com.usermanager.api.domain.dto.UserRequestDTO;
import com.usermanager.api.domain.dto.UserResponseDTO;
import com.usermanager.api.repository.UserRepository;
import com.usermanager.api.security.TokenService;
import com.usermanager.api.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
public class UserController{

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        Optional<UserDetails> user = userRepository.findByEmail(userRequestDTO.email());
        if(user.isEmpty()){
            userService.register(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else return ResponseEntity.badRequest().body("User already exists!");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> usersList(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Transactional
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid){
        userService.deleteByID(uuid);
        return ResponseEntity.ok().body("User removed sucessfully!");
    }
}