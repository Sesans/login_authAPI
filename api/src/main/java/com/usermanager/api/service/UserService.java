package com.usermanager.api.service;

import com.usermanager.api.domain.dto.UserRequestDTO;
import com.usermanager.api.domain.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void register(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> findAll();

    void deleteByID(UUID uuid);
}