package com.usermanager.api.repository;

import com.usermanager.api.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(@NotBlank(message = "E-mail field cannot be blank!") @Email(message = "Digite um E-mail válido!") String email);
}
