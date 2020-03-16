package com.example.registration.security;

import com.example.registration.domain.User;
import com.example.registration.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
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
        User user = new User(null, "bob", "123", true, "Canada", "Canada", null);
        ResponseEntity<String> response = template.postForEntity("http://localhost:8080/api/users", user, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void deleteUser() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");
        String url = "http://localhost:8080/api/users/2222";
        ResponseEntity<String> response = template.exchange(url, HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void updateUser() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");

        User user = new User(2222, "bob", "123", true, "Canada", "Canada", null);

        HttpEntity<User> request = new HttpEntity<>(user, new HttpHeaders());
        ResponseEntity<String> response =
                template.exchange("http://localhost:8080/api/users/", HttpMethod.PUT, request, String.class);
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
