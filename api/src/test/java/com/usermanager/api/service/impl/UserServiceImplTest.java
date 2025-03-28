package com.usermanager.api.service.impl;

import com.usermanager.api.domain.User;
import com.usermanager.api.domain.UserRole;
import com.usermanager.api.dto.UserRequestDTO;
import com.usermanager.api.dto.UserResponseDTO;
import com.usermanager.api.producer.UserProducer;
import com.usermanager.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserProducer userProducer;
    @InjectMocks
    private UserServiceImpl userService;

    private UserRequestDTO userRequestDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        userRequestDTO = new UserRequestDTO("test", LocalDate.of(1999, 10, 10),
                        "test@gmail.com", "pass@worD123", UserRole.USER);

        user = new User();
        user.setId(userId);
        user.setName(userRequestDTO.name());
        user.setBirthDate(userRequestDTO.birthDate());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        user.setRole(userRequestDTO.role());
    }

    @Test
    void shouldRegisterSuccessfully(){
        when(passwordEncoder.encode(userRequestDTO.password())).thenReturn("EncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.register(userRequestDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnAllUsers(){
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals(user.getName(), users.get(0).name());
        assertEquals(user.getEmail(), users.get(0).email());
    }

    @Test
    void shouldDeleteUserById(){
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteByID(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}