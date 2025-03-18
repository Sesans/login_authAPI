package com.usermanager.api.controller;

import com.usermanager.api.domain.dto.UserRequestDTO;
import com.usermanager.api.domain.dto.UserResponseDTO;
import com.usermanager.api.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
public class UserController{
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(201).body(userService.register(userRequestDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> usersList(){
        return ResponseEntity.status(200).body(userService.findAll());
    }

    @Transactional
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid){
        userService.deleteByID(uuid);
        return ResponseEntity.status(204).body("User removed successfully!");
    }
}