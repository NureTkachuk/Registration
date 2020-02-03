package com.example.registration.service;


import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final ModelMapper mapper = new ModelMapper();

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        User userFromDb = getUserByUsername(user.getUsername());

        if(userFromDb != null) {
            return null;
        }
        user.setPassword(user.getPassword());
        user.setActive(true);
        return userRepository.save(user);
    }

    public UserDTO findUserById(int id) {
        return mapper.map(userRepository.findById(id), UserDTO.class);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(UserDTO user) {
        User found = mapper.map(userRepository.getOne(user.getId()), User.class);

        String username = user.getUsername();
        String country = user.getCountry();
        String region = user.getRegion();

        if(username != null & !username.isEmpty()) found.setUsername(username);
        if(country != null & !country.isEmpty()) found.setCountry(country);
        if(region != null & !region.isEmpty()) found.setRegion(region);

        User savedUser = userRepository.save(found);
        return mapper.map(savedUser, UserDTO.class);
    }
}
