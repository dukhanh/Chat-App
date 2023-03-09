package com.example.demo.controller;

import com.example.demo.common.message.MessageContext;
import com.example.demo.common.message.ResponseMessage;
import com.example.demo.config.security.service.UserDetailsImpl;
import com.example.demo.dto.AuthUserDTO;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.SignupRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("auth")
    public ResponseEntity<AuthUserDTO> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        AuthUserDTO authUser = this.authService.login(userDetails);

        return ResponseEntity.ok(authUser);
    }

    @PostMapping("signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        if (userService.findUserByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(MessageContext.EXIST_EMAIL));
        }

        if (userService.findUserByUsername(signupRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(MessageContext.EXIST_USERNAME));
        }
        authService.registerUser(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage(MessageContext.REGISTER_SUCCESS));
    }
}
