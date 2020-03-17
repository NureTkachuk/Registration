package com.example.registration.service;

import java.util.Collection;

public interface JwtService {
    String generateToken(String username, Collection<String> roles);
}
