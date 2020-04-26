package com.example.registration.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private boolean active;
    private String country;
    private String region;
}
