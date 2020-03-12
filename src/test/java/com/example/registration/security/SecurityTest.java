package com.example.registration.security;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class SecurityTest {

    @Test
    public void getAllUsers() {
        TestRestTemplate template = new TestRestTemplate("admin", "admin");
        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/users", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
