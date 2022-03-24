package com.example.springsecurity.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import static com.example.springsecurity.security.UserRoles.*;

@Repository("fake")
public class ApplicationUserDaoService implements ApplicationUserDao {
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUserDetails> findByUserName(String username) {
        return getUser()
                .stream()
                .filter(applicationUserDetails -> username.equals(applicationUserDetails.getUsername()))
                .findFirst();
    }
    
    public List<ApplicationUserDetails> getUser(){
        return Lists.newArrayList(
            new ApplicationUserDetails(
                    STUDENT.getGrantedAuthorities(), 
                    passwordEncoder.encode("password"), 
                    "student", 
                    true, 
                    true, 
                    true, 
                    true
            ),
            new ApplicationUserDetails(
                    ADMIN.getGrantedAuthorities(),
                    passwordEncoder.encode("password"), 
                    "admin", 
                    true, 
                    true, 
                    true, 
                    true
            ),
            new ApplicationUserDetails(
                    MANAGER.getGrantedAuthorities(),
                    passwordEncoder.encode("password"), 
                    "manager", 
                    true, 
                    true, 
                    true, 
                    true
            )
        );
    }
}
