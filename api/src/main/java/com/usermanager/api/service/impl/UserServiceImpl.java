package com.usermanager.api.service.impl;

import com.usermanager.api.domain.User;
import com.usermanager.api.domain.UserRole;
import com.usermanager.api.dto.UserRequestDTO;
import com.usermanager.api.dto.UserResponseDTO;
import com.usermanager.api.producer.UserProducer;
import com.usermanager.api.repository.UserRepository;
import com.usermanager.api.service.UserService;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserProducer userProducer;

    @Override
    public String register(UserRequestDTO dto) {
        Optional<UserDetails> user = userRepository.findByEmail(dto.email());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setName(dto.name());
            newUser.setBirthDate(dto.birthDate());
            newUser.setEmail(dto.email());
            newUser.setPassword(passwordEncoder.encode(dto.password()));
            newUser.setRole(dto.role());
            userRepository.save(newUser);
            try{
                userProducer.publishRegistrationEmail(newUser);
            }catch (AmqpException exception){
                throw new AmqpException(exception);
            }
            return "User created successfully!";
        }else{
            return "User already exists!";
        }
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getBirthDate(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByID(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}