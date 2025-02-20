package com.usermanager.api.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name, LocalDate birthDate, String Email) {
}
