package com.example.springsecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/student")
public class ManagementController {

    private final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "AbdeNassar"),
            new Student(2, "Maria"),
            new Student(3, "Med"),
            new Student(4, "Youssef")
    );
    
    @GetMapping("/")
    @PreAuthorize("hasAuthority('student:read')")
    public List<Student> getAllStudent(){
        return STUDENTS;
    }
    
    @DeleteMapping(path = "/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public String deleteStudent(@PathVariable Integer studentId){
//        STUDENTS.remove(studentId);
        return "Removed from The List " + studentId;
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String addStudent(@RequestBody Student student){
//        STUDENTS.add(student);
        return "Added To The List";
    }
    
    @PutMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public String updateStudent(@PathVariable Integer studentId, @RequestBody Student student){
        STUDENTS.set(studentId, student);
        return "Updated Successfully";
    }
    
}
