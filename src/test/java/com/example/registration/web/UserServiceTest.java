package com.example.registration.web;


import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import com.example.registration.service.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private ModelMapper mapper;


    @Test
    public void getAllUsers() {
       List<User> expectedUsers  =
               Arrays.asList(new UserDTO(1, "admin", "admin", true, "USA", "California"),
                             new UserDTO(2, "user", "user", true, "USA", "Florida"))
               .stream()
               .map(UserDTO -> mapper.map(UserDTO, User.class))
               .collect(toList());

       userRepository.saveAll(expectedUsers);
       List<User> actualUsers =  userRepository.findAll();

       assertEquals(expectedUsers, actualUsers);

    }
}
