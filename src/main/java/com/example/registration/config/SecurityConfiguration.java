package com.example.registration.config;

import com.example.registration.repository.UserRepository;
import com.example.registration.web.security.JwtAuthorizationFilter;
import com.example.registration.service.security.JwtProperties;
import com.example.registration.service.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true, // PreAuthorize
        securedEnabled = true, // Secured
        jsr250Enabled = true)  // RoleAllowed
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserRepository userRepository;
    private JwtProperties jwtProperties;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //HTTP Basic authentication
//                .httpBasic()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .csrf().disable()
//                .formLogin().disable();

        http
                .httpBasic().disable()
                .csrf().disable()
                .addFilterAfter(new JwtAuthorizationFilter(jwtProperties), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().authenticated();

    }
}
