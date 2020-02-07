package com.example.registration.web;

import com.example.registration.service.dto.UserDTO;
import com.example.registration.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public UserDTO createUser(UserDTO user) {
        return userService.createUser(user);
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping
    public UserDTO updateUser(UserDTO user) {
        return userService.updateUser(user);
    }

}
