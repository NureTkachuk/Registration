package com.example.registration;

import com.example.registration.domain.User;
import com.example.registration.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Component
class AdminCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    public AdminCommandLineRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = userRepository.findByUsername("admin");
        if(admin == null) {
            User adm = new User();
            adm.setUsername("admin");
            adm.setPassword("admin");
            adm.setActive(true);
            adm.setCountry("USA");
            adm.setRegion("California");
            userRepository.save(adm);
        }
        User user = userRepository.findByUsername("user");
        if(user == null) {
            User u = new User();
            u.setUsername("user");
            u.setPassword("user");
            u.setActive(true);
            u.setCountry("USA");
            u.setRegion("Florida");
            userRepository.save(u);
        }
    }
}




