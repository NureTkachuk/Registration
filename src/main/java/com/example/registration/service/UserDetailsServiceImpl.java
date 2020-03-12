package com.example.registration.service;

import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromBD = userRepository.findByUsername(username);
             return new org.springframework.security.core.userdetails.User(
                     userFromBD.getUsername(),
                     userFromBD.getPassword(),
                     userFromBD.getRoles());
    }
}
