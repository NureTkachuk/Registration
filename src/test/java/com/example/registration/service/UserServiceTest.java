package com.example.registration.service;

import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import com.example.registration.service.dto.UserDTO;
import com.example.registration.web.BusinessException;
import com.example.registration.web.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private ModelMapper mapper;


    @Test
    public void createUser() {
        UserDTO expectedUser = userService.createUser(new UserDTO(null, "Bob", "12345", true, "Canada", "RegionName"));
        UserDTO actualUser = userService.findUserById(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void getUserById() {
        UserDTO expectedUser = userService.createUser(new UserDTO(null, "Bob", "12345", true, "Canada", "RegionName"));
        UserDTO actualUser = userService.findUserById(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateUser() {
        UserDTO user = userService.createUser(new UserDTO(null, "Bob", "12345", true, "Canada", "RegionName"));

        user.setUsername("test");
        user.setCountry("Canada");
        user.setRegion("Ontario");

        UserDTO actualUser = userService.updateUser(user);

        assertEquals(user, actualUser);
    }

    @Test
    public void getAllUsers() {
       List<UserDTO> expectedUsers = Arrays.asList(
               userService.createUser( new UserDTO(null, "admin", "admin", true, "USA", "California")),
               userService.createUser(new UserDTO(null, "user", "user", true, "USA", "Delaware")));

       List<UserDTO> actualUsers = userService.findAllUsers();

       assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void deleteUser() {
        UserDTO user = userService.createUser(new UserDTO(null, "Bob", "12345", true, "Canada", "RegionName"));

        assertNotNull(userService.findUserById(user.getId()));

        userService.deleteUserById(user.getId());

        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(user.getId()));
    }

    @Test
    public void entityNotFoundExceptionThrown() {
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(1));
    }

    @Test
    public void businessExceptionThrown() {
        UserDTO user = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        userService.createUser(user);

        assertThrows(BusinessException.class, () -> userService.createUser(user));
    }
}
