package com.example.registration.web;

import com.example.registration.service.dto.UserDTO;
import com.example.registration.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    @Test
    public void getUsers() {
        when(userService.findAllUsers()).thenReturn(Stream
        .of(new UserDTO(1, "admin", "admin", true, "USA", "California"),
            new UserDTO(2, "user", "user", true, "USA", "Florida"))
                .collect(Collectors.toList()));

        assertEquals(2, userService.findAllUsers().size());
    }

//    @Test
//    public void createUser() {
//        UserDTO user = new UserDTO();
//        when(userService.createUser(user)).thenReturn(user);
//        assertEquals(user, userService.createUser(user));
//    }

    @Test
    public void deleteUser() {
        UserDTO user = new UserDTO();
        userService.deleteUserById(user.getId());
        verify(userService, times(1)).deleteUserById(user.getId());
    }


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
        List<UserDTO> expectedUsers =
                Arrays.asList(new UserDTO(1, "admin", "admin", true, "USA", "California"),
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


     // DOESN'T WORK
    @Test
    public void createUser() throws Exception {
        UserDTO expectedUser = new UserDTO(3, "Bob", "12345", true, "Canada", "Canada Region");

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


    // DOESN'T WORK

    @Test
    public void updateUser() throws Exception {
        UserDTO updateUser = new UserDTO(2, "", "", true, "", "California");
        UserDTO expectedUser = new UserDTO(2, "user", "user", true, "USA", "California");



        given(userService.updateUser(updateUser)).willReturn(expectedUser);

        String json = mockMvc.perform(MockMvcRequestBuilders.put("/api/users", expectedUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO actualUser = objectMapper.readValue(json, UserDTO.class);

        assertEquals(expectedUser, actualUser);
    }
}
