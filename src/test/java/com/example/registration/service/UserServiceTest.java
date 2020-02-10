package com.example.registration.service;

import com.example.registration.service.dto.UserDTO;
import com.example.registration.web.BusinessException;
import com.example.registration.web.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private  UserService userService;



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
        userService.createUser(new UserDTO(null, "Bob", "12345", true, "Canada", "RegionName"));

        assertThrows(BusinessException.class,
                () -> userService.createUser(new UserDTO(null, "Bob", "12345", true, "Canada", "RegionName")));
    }
}
