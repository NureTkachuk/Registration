package com.example.registration.service.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public interface JwtService {
    String generateToken(String username, Collection<String> roles);
    void doFilterInternal(HttpServletRequest request,
                     HttpServletResponse response,
                     FilterChain chain) throws ServletException, IOException;
}
