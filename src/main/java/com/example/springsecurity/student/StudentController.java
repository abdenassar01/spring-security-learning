package com.example.springsecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    
    private final List<Student> students = Arrays.asList(
            new Student(1, "AbdeNassar"),
            new Student(2, "Maria"),
            new Student(3, "Med"),
            new Student(4, "Youssef")
    );
    
    @GetMapping("/all")
    public List<Student> getAll(){
        return students;
    }
    
    @GetMapping(path = "/{id}")
    public Student getStudent(@PathVariable("id") Integer id){
        return students
                .stream()
                .filter(student -> id.equals(student.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Student Not Found"));
    }
    
}
