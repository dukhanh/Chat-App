package com.example.demo.service.impl;

import com.example.demo.config.security.jwt.JwtUtils;
import com.example.demo.config.security.service.UserDetailsImpl;
import com.example.demo.dto.AuthUserDTO;
import com.example.demo.entity.User;
import com.example.demo.payload.SignupRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public AuthServiceImpl(PasswordEncoder encoder, JwtUtils jwtUtils, ModelMapper modelMapper, UserRepository userRepository) {
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public AuthUserDTO login(UserDetailsImpl user) {
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        String jwt = jwtUtils.generateJwtToken(user.getUsername(), roles);
        return new AuthUserDTO(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Override
    public void registerUser(SignupRequest newUser) {
        User user = this.modelMapper.map(newUser, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
