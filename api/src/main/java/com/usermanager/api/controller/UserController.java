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
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
public class UserController{

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> userRegister(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userService.register(userRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> usersList(){
        return ResponseEntity.ok().body(userService.findAll());
    }
    @Transactional
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID uuid){
        userService.deleteByID(uuid);
        return ResponseEntity.ok().build();
    }
}