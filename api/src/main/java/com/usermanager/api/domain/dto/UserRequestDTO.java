package com.usermanager.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank(message = "Name field cannot be blank!") String name,
        @NotNull(message = "Birthday field cannot be null!") Long birthDate,
        @NotBlank(message = "E-mail field cannot be blank!")@Email(message = "Digite um E-mail válido!") String email,
        @NotBlank(message = "Password field cannot be blank!") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must be at least 8 characters long, including 1 uppercase, 1 lowercase, a number and a special character ") String password
) {
}
