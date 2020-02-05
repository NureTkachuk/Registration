package com.example.registration.service;


import com.example.registration.service.dto.UserDTO;
import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final ModelMapper mapper = new ModelMapper();

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        for(User user : userRepository.findAll())
            users.add(mapper.map(user, UserDTO.class));
        return users;
    }

    public UserDTO createUser(UserDTO user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null) {
            return null;
        }
        User createdUser = mapper.map(user, User.class);
        createdUser.setActive(true);
        createdUser.setPassword(user.getPassword());
        return mapper.map(userRepository.save(createdUser), UserDTO.class);
    }

    public UserDTO findUserById(int id) {
        return mapper.map(userRepository.findById(id), UserDTO.class);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapper.map(user, UserDTO.class);
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
