package com.example.registration.web;

import com.example.registration.service.AuthRequest;
import com.example.registration.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(consumes = "application/json")
    public String login(@RequestBody AuthRequest authRequest) {
        return  authService.login(authRequest);
    }
}
