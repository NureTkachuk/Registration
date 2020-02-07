package com.example.registration.web;


import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import com.example.registration.service.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private ModelMapper mapper;


    @Test
    public void getAllUsers() {
       UserDTO admin = new UserDTO(1, "admin", "admin", true, "USA", "California");
       UserDTO user = new UserDTO(2, "user", "user", true, "USA", "Florida");
       userRepository.save(mapper.map(admin, User.class));
       userRepository.save(mapper.map(user, User.class));
       assertEquals(2, userService.findAllUsers().size());
    }
}
