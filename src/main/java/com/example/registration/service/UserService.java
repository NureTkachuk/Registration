package com.example.registration.service;


import com.example.registration.service.dto.UserDTO;
import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.web.BusinessException;
import com.example.registration.web.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper = MapperConfiguration.modelMapper();

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO createUser(UserDTO user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null) {
            throw new BusinessException("User already exists!");
        }
        User createdUser = mapper.map(user, User.class);
        createdUser.setActive(true);
        createdUser.setPassword(user.getPassword());
        return mapper.map(userRepository.save(createdUser), UserDTO.class);
    }

    @Transactional(readOnly = true)
    public UserDTO findUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User is not found!"));
        return mapper.map(user, UserDTO.class);
    }

    @Transactional
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User is not found!"));
        userRepository.deleteById(user.getId());
    }

    @Transactional
    public UserDTO updateUser(UserDTO user) {
        User found = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User is not found!"));

        String username = user.getUsername();
        String country = user.getCountry();
        String region = user.getRegion();

        if(username != null && !username.isEmpty()) found.setUsername(username);
        if(country != null && !country.isEmpty()) found.setCountry(country);
        if(region != null && !region.isEmpty()) found.setRegion(region);

        User savedUser = userRepository.save(found);
        return mapper.map(savedUser, UserDTO.class);
    }
}
