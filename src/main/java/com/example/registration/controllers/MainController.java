package com.example.registration.controllers;


import com.example.registration.model.Role;
import com.example.registration.model.User;
import com.example.registration.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/home")
    public String main() {
        return "home";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/userPage")
    public String user(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        model.put("users", users);
        return "userPage";
    }

    @GetMapping("/adminPage")
    public String admin(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        model.put("users", users);
        return "adminPage";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null) {
            model.put("message", "user exists");
            return "registration";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("user/edit/{id}")
    public String userEdit(@PathVariable Integer id, Map<String, Object> model) {
        Optional<User> user = userRepository.findById(id);
        model.put("user",  user.get());
        return "userEdit";
    }

    @GetMapping("user/delete/{id}")
    public String userDelete(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "redirect:/adminPage";
    }

    @PostMapping("/user/{id}")
    public String userChangeSave(@PathVariable Integer id, @RequestParam String username, @RequestParam String country,
                                 @RequestParam String region) {
        User user = userRepository.getOne(id);
        if(!username.equals("")) user.setUsername(username);
        if(!country.equals("")) user.setCountry(country);
        if(!region.equals("")) user.setRegion(region);
        userRepository.save(user);
        return "redirect:/adminPage";
    }

}
