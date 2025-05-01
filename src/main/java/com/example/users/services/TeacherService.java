package com.example.users.services;

import com.example.users.entities.Teacher;
import com.example.users.enums.Role;
import com.example.users.repositories.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final UsersServices usersServices;
    private final TeacherRepo teacherRepo;

    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    public Optional<Teacher> getTeacherById(String id) {
        return teacherRepo.findById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return (Teacher) usersServices.add(teacher);
    }

    public void deleteTeacher(String id) {
        teacherRepo.deleteById(id);
    }
}
