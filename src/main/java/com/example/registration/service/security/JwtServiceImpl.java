package com.example.registration.service.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;

    @Override
    public String generateToken(String username, Collection<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setExpiration(new Date(Instant.now().plusSeconds(jwtProperties.getTtl()).toEpochMilli()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret().getBytes())
                .compact();
    }
}
