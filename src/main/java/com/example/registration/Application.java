package com.example.registration;

import com.example.registration.model.Role;
import com.example.registration.model.User;
import com.example.registration.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Component
class AdminCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin = userRepository.findByUsername("admin");
        if(admin == null) {
            User adm = new User();
            adm.setUsername("admin");
            adm.setPassword(new BCryptPasswordEncoder().encode("admin"));
            adm.setActive(true);
            adm.setCountry("USA");
            adm.setRegion("California");
            adm.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(adm);
        }
        User user = userRepository.findByUsername("user");
        if(user == null) {
            User u = new User();
            u.setUsername("user");
            u.setPassword(new BCryptPasswordEncoder().encode("user"));
            u.setActive(true);
            u.setCountry("USA");
            u.setRegion("Florida");
            u.setRoles(Collections.singleton(Role.USER));
            userRepository.save(u);
        }
    }
}




