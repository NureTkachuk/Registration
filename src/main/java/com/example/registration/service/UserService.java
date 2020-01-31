package com.example.registration.service;


import com.example.registration.model.Role;
import com.example.registration.model.User;
import com.example.registration.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        User userFromDb = (User) loadUserByUsername(user.getUsername());

        if(userFromDb != null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    public User findUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        User found = userRepository.getOne(user.getId());
        String username = user.getUsername();
        String country = user.getCountry();
        String region = user.getRegion();
        if(username != null & !username.isEmpty()) found.setUsername(username);
        if(country != null & !country.isEmpty()) found.setCountry(country);
        if(region != null & !region.isEmpty()) found.setRegion(region);
       return userRepository.save(found);
    }
}
