package com.usermanager.api.dto;

import com.usermanager.api.domain.UserRole;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name, LocalDate birthDate, String email, UserRole role) {
}
