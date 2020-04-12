package com.example.registration.security;

import com.example.registration.domain.Role;
import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.security.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class SecurityTest {

    @Autowired
    private UserRepository userRepository;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void setup() {
        createAdminUser();
        createRegularUser();
    }

    @Test
    public void getAllUsersAsAdmin() {

        String adminJwt = loginAsAdmin();

        RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8080/api/users"))
            .header("Authorization", "Bearer " + adminJwt)
            .build();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getAllUsersAsUser() {

        String userJwt = loginAsUser();

        RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8080/api/users"))
            .header("Authorization", "Bearer " + userJwt)
            .build();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void getUserById() {

        String userJwt = loginAsUser();

        RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8080/api/users/1"))
            .header("Authorization", "Bearer " + userJwt)
            .build();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void createUser() {
        User user = new User(null, "bob", "123", true, "Canada", "Canada", null);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/users", user, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void deleteUser() {
        String url = "http://localhost:8080/api/users/2222";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void updateUser() {

        User user = new User(2222, "bob", "123", true, "Canada", "Canada", null);

        HttpEntity<User> request = new HttpEntity<>(user, new HttpHeaders());
        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:8080/api/users/", HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getAllUsersMissingJwt() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/users/", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void getUserMissingJwt() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/users/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void deleteUserMissingJwt() {
        String url = "http://localhost:8080/api/users/2222";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void updateUserMissingJwt() {
        String url = "http://localhost:8080/api/users/2222";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void createUserMissingJwt() {
        String url = "http://localhost:8080/api/users/2222";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    private String loginAsAdmin() {
        return login("admin", "admin");
    }

    private String loginAsUser() {
        return login("user", "user");
    }

    private String login(String username, String password) {

        RequestEntity<String> authRequest = RequestEntity.post(URI.create("http://localhost:8080/auth"))
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");

        ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(authRequest, AuthResponse.class);
        return authResponse.getBody().getAccessToken();
    }

    private void createAdminUser() {

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        admin.setActive(true);
        admin.setCountry("USA");
        admin.setRegion("California");
        admin.setRoles(Collections.singleton(Role.ADMIN));

        userRepository.save(admin);
    }

    private void createRegularUser() {

        User admin = new User();
        admin.setUsername("user");
        admin.setPassword(new BCryptPasswordEncoder().encode("user"));
        admin.setActive(true);
        admin.setCountry("USA");
        admin.setRegion("California");
        admin.setRoles(Collections.singleton(Role.USER));

        userRepository.save(admin);
    }
}
