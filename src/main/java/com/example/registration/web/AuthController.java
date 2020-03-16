package com.example.registration.web;

import com.example.registration.service.TokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String login(@RequestParam String username, @RequestParam String password) {
        return tokenService.generateToken(username, null); // TODO
    }
}
