
package com.example.studentcourse.controller;

import com.example.studentcourse.model.Student;
import com.example.studentcourse.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    // Constructor Injection
    public StudentController(StudentService service) {
        this.service = service;
    }

    // 1. Add Student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@Valid @RequestBody Student student) {
        return service.addStudent(student);
    }

    // 2. Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    // 3. Get Student By ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    // 4. Update Complete Student (PUT)
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {

        return service.updateStudent(id, student);
    }

    // 5. Delete Student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return service.deleteStudent(id);
    }

    // 6. Partial Update (PATCH)
    @PatchMapping("/{id}")
    public Student patchStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return service.patchStudent(id, student);
    }
}
