package com.example.users.services;

import com.example.users.entities.Student;
import com.example.users.enums.Role;
import com.example.users.repositories.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UsersServices usersServices;
    private final StudentRepo studentRepo;

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Optional<Student> getStudentById(String id) {
        return studentRepo.findById(id);
    }

    public Student saveStudent (Student student) {
        student.setRole(Role.STUDENT);
        return (Student) usersServices.add(student);
    }

    public void deleteStudent(String id) {
        studentRepo.deleteById(id);
    }
}
