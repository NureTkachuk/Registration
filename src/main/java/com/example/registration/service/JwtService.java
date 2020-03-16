package com.example.registration.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JwtService {
    String generateToken(String username, Collection<? extends GrantedAuthority> roles);
}
