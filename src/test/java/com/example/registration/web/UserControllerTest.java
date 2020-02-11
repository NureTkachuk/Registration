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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
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


    @Test
    @WithMockUser
    public void findUserById() throws Exception {

        UserDTO expectedUser = new UserDTO();
        expectedUser.setId(1);
        expectedUser.setUsername("user");

        given(userService.findUserById(1)).willReturn(expectedUser);

        String json = mockMvc.perform(get("/api/users/1")
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
    @WithMockUser
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
    @WithMockUser
    public void createUser() throws Exception {
        UserDTO expectedUser = new UserDTO(1, "user", "user", true, "USA", "Florida");

        given(userService.createUser(expectedUser)).willReturn(expectedUser);

        String json = mockMvc.perform(post("/api/users")
                .content(objectMapper.writeValueAsString(expectedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO actualUser = objectMapper.readValue(json, UserDTO.class);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    public void updateUser() throws Exception {
        UserDTO expectedUser = new UserDTO(2, "user", "user", true, "USA", "Kansas");

        given(userService.updateUser(expectedUser)).willReturn(expectedUser);

        String json = mockMvc.perform(put("/api/users")
                .content(objectMapper.writeValueAsString(expectedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO actualUser = objectMapper.readValue(json, UserDTO.class);

        assertEquals(expectedUser, actualUser);

    }

    @Test
    @WithMockUser
    public void findUserByIdNotFound() throws Exception {
        //given(userService.findUserById(11)).willThrow(new EntityNotFoundException("User is not found!"));
        when(userService.findUserById(1)).thenThrow(new EntityNotFoundException("User is not found!"));

        mockMvc.perform(get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void createUserBusinessException() throws Exception {
        UserDTO user = new UserDTO(1, "user", "", true, "", "");
        when(userService.createUser(user)).thenThrow(new BusinessException("User already exists!"));

        mockMvc.perform(post("/api/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());
    }

}
