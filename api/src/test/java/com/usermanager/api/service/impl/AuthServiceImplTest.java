package com.usermanager.api.service.impl;

import com.usermanager.api.domain.User;
import com.usermanager.api.dto.AuthenticationDTO;
import com.usermanager.api.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @InjectMocks
    AuthServiceImpl authService;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    TokenService tokenService;

    @Test
    void shouldAuthenticateAndReturnToken() {
        String login = "user@example.com";
        String password = "securepassword";

        AuthenticationDTO dto = new AuthenticationDTO(login, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, password);

        User mockUser = mock(User.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, password);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        when(tokenService.generateToken(mockUser)).thenReturn("mocked-jwt");

        String token = authService.authenticate(dto);

        assertNotNull(token);
        assertEquals("mocked-jwt", token);
        verify(authenticationManager).authenticate(authenticationToken);
        verify(tokenService).generateToken(mockUser);
    }

    @Test
    void shouldFailInAuthenticationAndThrowException(){
        AuthenticationDTO dto = new AuthenticationDTO("invalid login", "invalid password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());

        when(authenticationManager.authenticate(authenticationToken)).thenThrow(new BadCredentialsException("Invalid credentials!"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> authService.authenticate(dto));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Invalid credentials!", exception.getReason());
        verify(authenticationManager).authenticate(authenticationToken);
        verifyNoInteractions(tokenService);
    }
}