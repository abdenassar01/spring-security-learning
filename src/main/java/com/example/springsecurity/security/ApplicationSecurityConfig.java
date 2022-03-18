package com.example.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index")
                .permitAll()
                .antMatchers("/api/**").hasRole(UserRoles.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin =  User
                .builder()
                .username("abdenassar")
                .password(passwordEncoder.encode("supersecretpassword"))
                .roles(UserRoles.ADMIN.name())
                .build();
        
        UserDetails student =  User
                .builder()
                .username("maria")
                .password(passwordEncoder.encode("weakpassword"))
                .roles(UserRoles.STUDENT.name())
                .build();
        
        return new InMemoryUserDetailsManager(admin, student);
    }
}
