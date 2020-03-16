package com.example.registration.service;

import com.example.registration.domain.Role;

import java.util.List;

public interface TokenService {
    String generateToken(String username, List<Role> roles);
}
