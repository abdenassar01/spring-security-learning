package com.example.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.springsecurity.security.UserRoles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  I was messing the csrf working -> should disable it 
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/courses")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .and()
                    .rememberMe();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin =  User
                .builder()
                .username("abdenassar")
                .password(passwordEncoder.encode("supersecretpassword"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails manager =  User
                .builder()
                .username("manager")
                .password(passwordEncoder.encode("meduimpassword"))
                .authorities(MANAGER.getGrantedAuthorities())
                .build();

        UserDetails student =  User
                .builder()
                .username("maria")
                .password(passwordEncoder.encode("weakpassword"))
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        
        
        return new InMemoryUserDetailsManager(admin, manager,  student);
    }
}
