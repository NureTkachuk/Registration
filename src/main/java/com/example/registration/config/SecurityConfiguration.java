package com.example.registration.config;

import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("ADMIN") // get by Id
                .antMatchers(HttpMethod.GET, "/api/users/").hasAnyAuthority("USER", "ADMIN") // get all
                .antMatchers(HttpMethod.POST, "/api/users/").hasAuthority("USER") // create
                .antMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("ADMIN") // update
                .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN") // delete
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
