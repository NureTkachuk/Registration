package com.example.registration.security;

import com.example.registration.domain.Role;
import com.example.registration.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class SecurityTest {

    @MockBean
    private UserService userService;

    @Test
    public void getAllUsers() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");
        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/users", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getUserById() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");
        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/users", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void createUser() {
        TestRestTemplate template = new TestRestTemplate("user", "user");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        User user = new User(3, "bob", "123", true, "Canada", "Canada",roles);
        HttpEntity<User> request = new HttpEntity<>(user, new HttpHeaders());
        ResponseEntity<String> response = template.postForEntity("http://localhost:8080/api/users", request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

    }

    @Test
    public void deleteUser() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");
        String url = "http://localhost:8080/api/users/{id}";
        Integer id = 2;
        HttpEntity<Integer> request = new HttpEntity<>(id, new HttpHeaders());
        ResponseEntity<String> response = template.exchange(url, HttpMethod.DELETE, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void updateUser() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        User user = new User(2, "bob", "123", true, "Canada", "Canada",roles);

        HttpEntity<User> request = new HttpEntity<>(user, new HttpHeaders());
        ResponseEntity<User> response =
                template.exchange("http://localhost:8080/api/users/", HttpMethod.PUT, request, User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getAllUsersByUser() {
        TestRestTemplate template = new TestRestTemplate("user", "user");
        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/users/", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void deleteUserByUser() {
        TestRestTemplate template = new TestRestTemplate("user", "user");
        String url = "http://localhost:8080/api/users/2222";
        ResponseEntity<String> response = template.exchange(url, HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }
}
