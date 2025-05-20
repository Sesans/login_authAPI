package com.usermanager.api.controller;

import com.usermanager.api.dto.AuthResponseDTO;
import com.usermanager.api.dto.AuthenticationDTO;
import com.usermanager.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> userLogin(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String token = authService.authenticate(authenticationDTO);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
