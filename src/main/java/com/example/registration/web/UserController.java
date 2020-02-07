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

    @GetMapping(produces = "application/json")
    public List<UserDTO> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public UserDTO updateUser(@RequestBody UserDTO user) {
        return userService.updateUser(user);
    }

}
