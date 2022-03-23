package com.example.springsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    
    @GetMapping("login")
    public String login(){
       return "login"; 
    }
    
    @GetMapping("courses")
    public String getCourses(){
        return "courses";
    }
}
