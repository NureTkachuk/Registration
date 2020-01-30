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

    public boolean addUser(User user) {
        User userFromDb = (User) loadUserByUsername(user.getUsername());

        if(userFromDb != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return true;
    }

    public User findUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public void userChangeSave(Integer id, String username, String country,
                               String region) {

        User user = userRepository.getOne(id);
        if(!username.equals("")) user.setUsername(username);
        if(!country.equals("")) user.setCountry(country);
        if(!region.equals("")) user.setRegion(region);
        userRepository.save(user);
    }
}
