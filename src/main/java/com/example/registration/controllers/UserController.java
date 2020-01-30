package com.example.registration.controllers;

import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", userService.findAllUsers());
        modelAndView.setViewName("userPage");
        return modelAndView;
    }

    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", userService.findAllUsers());
        modelAndView.setViewName("adminPage");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView addUser(User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (!userService.addUser(user)) {
            modelAndView.setViewName("registration");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @RequestMapping(value = "user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView userEdit(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(id);
        modelAndView.addObject("user",  user);
        modelAndView.setViewName("userEdit");
        return modelAndView;
    }

    @RequestMapping(value = "user/delete/{id}", method = RequestMethod.GET)
    public ModelAndView userDelete(@PathVariable Integer id) {
        userService.deleteUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/adminPage");
        return modelAndView;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public ModelAndView userChangeSave(@PathVariable Integer id, @RequestParam String username, @RequestParam String country,
                                 @RequestParam String region) {

        userService.userChangeSave(id, username, country, region);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/adminPage");
        return modelAndView;
    }

}
