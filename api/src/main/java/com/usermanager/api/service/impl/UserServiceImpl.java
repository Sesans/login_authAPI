package com.usermanager.api.service.impl;

import com.usermanager.api.domain.User;
import com.usermanager.api.domain.dto.UserRequestDTO;
import com.usermanager.api.domain.dto.UserResponseDTO;
import com.usermanager.api.repository.UserRepository;
import com.usermanager.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void register(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.name());
        setUserBirthDate(user, userRequestDTO.birthDate());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getBirthDate(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByID(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    public void setUserBirthDate(User user, long timestamp) {
        LocalDate birthDate = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        user.setBirthDate(birthDate);
    }
}
