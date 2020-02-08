package com.example.registration.web;

import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import com.example.registration.service.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private ModelMapper mapper;


    @Test
    public void createUser() {
        UserDTO expectedUser = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        UserDTO actualUser = userService.createUser(expectedUser);

        assertEquals(expectedUser, actualUser);

        List<UserDTO> expectedUserList = Arrays.asList(expectedUser);
        List<UserDTO> actualUserList = userService.findAllUsers();
        assertEquals(expectedUserList, actualUserList);
    }


    @Test
    public void getUserById() {
        UserDTO expectedUser = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        //User user = mapper.map(expectedUser, User.class);
        userService.createUser(expectedUser);
        UserDTO actualUser = userService.findUserById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateUser() {
        UserDTO expectedUser = new UserDTO(1, "Bill", "12345", true, "USA", "California");
        UserDTO user = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        userService.createUser(user);
        UserDTO actualUser = userService.updateUser(expectedUser);

        assertEquals(expectedUser, actualUser);

        List<UserDTO> expectedUserList = Arrays.asList(expectedUser);
        List<UserDTO> actualUserList = userService.findAllUsers();

        assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void getAllUsers() {
       List<UserDTO> expectedUsers = new ArrayList<>();
        expectedUsers.add(userService.createUser( new UserDTO(1, "admin", "admin", true, "USA", "California")));
        expectedUsers.add(userService.createUser(new UserDTO(2, "user", "user", true, "USA", "Delaware")));

       List<UserDTO> actualUsers = userService.findAllUsers();

       assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void deleteUser() {
        UserDTO user = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        userService.createUser(user);

        List<UserDTO> expectedUserList = Arrays.asList(user);
        List<UserDTO> actualUserList = userService.findAllUsers();

        assertEquals(expectedUserList, actualUserList);

        userService.deleteUserById(1);

        expectedUserList = new ArrayList<>();
        actualUserList = userService.findAllUsers();

        assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void entityNotFoundExceptionThrown() {
        UserDTO user = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        userService.createUser(user);

        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(5));
    }

    @Test
    public void businessExceptionThrown() {
        UserDTO user = new UserDTO(1, "Bob", "12345", true, "Canada", "RegionName");
        userService.createUser(user);

        assertThrows(BusinessException.class, () -> userService.createUser(user));
    }
}
