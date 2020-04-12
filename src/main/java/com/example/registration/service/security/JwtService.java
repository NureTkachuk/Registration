package com.example.registration.service.security;

import io.jsonwebtoken.Claims;

import java.util.Collection;

public interface JwtService {
    String generateToken(String username, Collection<String> roles);
    Claims parseToken(String jwt);
}
