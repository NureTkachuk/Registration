package com.example.registration.controllers;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @GetMapping(path = "{id}")
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
