package com.example.registration.service.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    private String accessToken;
}
