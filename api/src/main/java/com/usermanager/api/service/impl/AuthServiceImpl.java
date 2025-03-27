package com.usermanager.api.service.impl;

import com.usermanager.api.domain.User;
import com.usermanager.api.dto.AuthenticationDTO;
import com.usermanager.api.security.TokenService;
import com.usermanager.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @Override
    public String authenticate(AuthenticationDTO authenticationDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            return tokenService.generateToken((User) auth.getPrincipal());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }
    }
}
