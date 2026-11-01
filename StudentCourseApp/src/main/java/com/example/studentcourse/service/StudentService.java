
package com.example.studentcourse.service;

import com.example.studentcourse.model.Student;
import com.example.studentcourse.exception.StudentNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final ArrayList<Student> students = new ArrayList<>();

    private long nextId = 1L;

    // 1. Add Student
    public synchronized Student addStudent(Student request) {

        Student student = new Student(
                nextId++,
                request.getStudentName().trim(),
                request.getCourseName().trim(),
                request.getEmail().trim(),
                request.getAge()
        );

        students.add(student);

        return copyStudent(student);
    }

    // 2. Get All Students
    public synchronized List<Student> getAllStudents() {

        return students.stream()
                .map(this::copyStudent)
                .toList();
    }

    // 3. Get Student By ID
    public synchronized Student getStudentById(Long id) {

        return copyStudent(findStudent(id));
    }

    // 4. Update Complete Student (PUT)
    public synchronized Student updateStudent(
            Long id, Student request) {

        Student student = findStudent(id);

        student.setStudentName(request.getStudentName().trim());
        student.setCourseName(request.getCourseName().trim());
        student.setEmail(request.getEmail().trim());
        student.setAge(request.getAge());

        return copyStudent(student);
    }

    // 5. Delete Student
    public synchronized String deleteStudent(Long id) {

        Student student = findStudent(id);

        students.remove(student);

        return "Student deleted successfully with ID: " + id;
    }

    // 6. Partial Update (PATCH)
    public synchronized Student patchStudent(
            Long id, Student request) {

        Student student = findStudent(id);

        if (request.getStudentName() != null) {

            if (request.getStudentName().isBlank()) {
                throw new IllegalArgumentException(
                        "Student name cannot be blank");
            }

            student.setStudentName(
                    request.getStudentName().trim());
        }

        if (request.getCourseName() != null) {

            if (request.getCourseName().isBlank()) {
                throw new IllegalArgumentException(
                        "Course name cannot be blank");
            }

            student.setCourseName(
                    request.getCourseName().trim());
        }

        if (request.getEmail() != null) {

            if (request.getEmail().isBlank()
                    || !request.getEmail().matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                throw new IllegalArgumentException(
                        "Enter a valid email");
            }

            student.setEmail(request.getEmail().trim());
        }

        if (request.getAge() != null) {

            if (request.getAge() < 18) {
                throw new IllegalArgumentException(
                        "Age must be at least 18");
            }

            student.setAge(request.getAge());
        }

        return copyStudent(student);
    }

    // Find Student
    private Student findStudent(Long id) {

        return students.stream()
                .filter(student -> student.getStudentId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID: " + id
                        ));
    }

    // Copy Student
    private Student copyStudent(Student student) {

        return new Student(
                student.getStudentId(),
                student.getStudentName(),
                student.getCourseName(),
                student.getEmail(),
                student.getAge()
        );
    }
}
