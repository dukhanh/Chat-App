package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {
    private String token;
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
