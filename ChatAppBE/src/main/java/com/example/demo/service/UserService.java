package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    User findUserByUsername(String username);

}
