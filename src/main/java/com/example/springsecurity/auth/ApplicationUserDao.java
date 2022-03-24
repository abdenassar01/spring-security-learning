package com.example.springsecurity.auth;


import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUserDetails> findByUserName(String username);
}
