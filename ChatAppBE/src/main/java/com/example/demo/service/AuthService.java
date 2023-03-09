package com.example.demo.service;

import com.example.demo.config.security.service.UserDetailsImpl;
import com.example.demo.dto.AuthUserDTO;
import com.example.demo.payload.SignupRequest;

public interface AuthService {
    AuthUserDTO login(UserDetailsImpl user);

    void registerUser(SignupRequest user);

}
