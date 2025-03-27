package com.usermanager.api.service;

import com.usermanager.api.dto.AuthenticationDTO;

public interface AuthService {
    String authenticate(AuthenticationDTO authenticationDTO);
}
