package com.example.registration.service;

import com.example.registration.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String SECRET_KEY = "RegistrationSecretKey";
    @Override
    public String generateToken(String username, List<Role> roles) {
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
