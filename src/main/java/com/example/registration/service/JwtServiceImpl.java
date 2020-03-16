package com.example.registration.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = "RegistrationSecretKey";
    @Override
    public String generateToken(String username, Collection<? extends GrantedAuthority> roles) {
        return Jwts
                .builder()
                .setSubject("Registration")
                .claim("name", username)
                .claim("roles", roles)
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY.getBytes()
                )
                .compact();
    }
}
