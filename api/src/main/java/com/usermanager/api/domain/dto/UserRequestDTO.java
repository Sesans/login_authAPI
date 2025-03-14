package com.usermanager.api.domain.dto;

import com.usermanager.api.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UserRequestDTO(
        @NotBlank(message = "Name field cannot be blank!") String name,
        @NotNull(message = "Birthday field cannot be null!") LocalDate birthDate,
        @NotBlank(message = "E-mail field cannot be blank!") @Email(message = "Digite um E-mail válido!") String email,
        @NotBlank(message = "Password field cannot be blank!") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[.@#$%^&+=!]).{8,}$",
                message = "Password must be at least 8 characters long, including 1 uppercase, 1 lowercase, a number and a special character ") String password,

        @NotNull UserRole role
) {
    public UserRequestDTO(String name, LocalDate birthDate, String email, String password, UserRole role){
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = (role != null) ? role : UserRole.USER;
    }
}
