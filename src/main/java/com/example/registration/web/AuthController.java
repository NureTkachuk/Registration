package com.example.registration.web;

import com.example.registration.service.AuthRequest;
import com.example.registration.service.AuthResponse;
import com.example.registration.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(consumes = "application/json")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return  authService.login(authRequest);
    }
}
