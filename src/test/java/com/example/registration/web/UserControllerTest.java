package com.example.registration.web;

import com.example.registration.service.dto.UserDTO;
import com.example.registration.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired UserController userController;


//    @Test
//    public void getUsers() {
//        when(userService.findAllUsers()).thenReturn(Stream
//        .of(new UserDTO(1, "admin", "admin", true, "USA", "California"),
//            new UserDTO(2, "user", "user", true, "USA", "Florida"))
//                .collect(Collectors.toList()));
//
//        assertEquals(2, userService.findAllUsers().size());
//    }

//    @Test
//    public void createUser() {
//        UserDTO user = new UserDTO();
//        when(userService.createUser(user)).thenReturn(user);
//        assertEquals(user, userService.createUser(user));
//    }

//    @Test
//    public void deleteUser() {
//        UserDTO user = new UserDTO();
//        userService.deleteUserById(user.getId());
//        verify(userService, times(1)).deleteUserById(user.getId());
//    }


//    @Test
//    public void updateUser() {
//        UserDTO expectedUser = new UserDTO(2, "user", "user", true, "USA", "California");
//        when(userService.updateUser(expectedUser)).thenReturn(expectedUser);
//        UserDTO actualUser = userService.updateUser(expectedUser);
//        assertEquals(expectedUser, actualUser);
//    }

    @Test
    public void findUserById() throws Exception {

        UserDTO expectedUser = new UserDTO();
        expectedUser.setId(2);
        expectedUser.setUsername("user");

        given(userService.findUserById(2)).willReturn(expectedUser);

        String json = mockMvc.perform(get("/api/users/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO actualUser = objectMapper.readValue(json, UserDTO.class);

        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void getAllUsers() throws Exception {
        List<UserDTO> expectedUsers = Arrays.asList(new UserDTO(1, "admin", "admin", true, "USA", "California"),
                                                    new UserDTO(2, "user", "user", true, "USA", "Florida"));

        given(userService.findAllUsers()).willReturn(expectedUsers);

        String json = mockMvc.perform(get("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDTO> actualUsers = objectMapper.readValue(json, new TypeReference<List<UserDTO>>() {});

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void createUser() throws Exception {
        UserDTO expectedUser = new UserDTO();

        given(userService.createUser(expectedUser)).willReturn(expectedUser);

        String json = mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO actualUser = objectMapper.readValue(json, UserDTO.class);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void deleteUser() throws Exception {
        UserDTO user = new UserDTO(2, "user", "user", true, "USA", "Florida");

        String json = mockMvc.perform(delete("/api/users/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(userService, times(1)).deleteUserById(user.getId());
    }

    @Test
    public void updateUser() throws Exception {
        UserDTO user = new UserDTO(2, "", "", true, "", "Kansas");
        UserDTO expectedUser = new UserDTO(2, "user", "user", true, "USA", "Kansas");

        given(userService.updateUser(user)).willReturn(expectedUser);

        String json = mockMvc.perform(put("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO actualUser = objectMapper.readValue(json, UserDTO.class);

        assertEquals(expectedUser, actualUser);

    }

    @Test
    public void findUserByIdNotFound() throws Exception {
        //given(userService.findUserById(11)).willThrow(new EntityNotFoundException("User is not found!"));


        when(userService.findUserById(11)).thenThrow(new EntityNotFoundException("User is not found!"));


        mockMvc.perform(get("/api/users/11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createUserBusinessException() throws Exception {
        UserDTO user = new UserDTO(1, "user", "", true, "", "");
        when(userService.createUser(user)).thenThrow(new BusinessException("User already exists!"));

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
