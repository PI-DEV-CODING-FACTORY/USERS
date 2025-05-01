package com.example.users.services;

import com.example.users.entities.Admin;
import com.example.users.repositories.AdminRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UsersServices usersServices;
    private final AdminRepo adminRepo;

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }
    public Optional<Admin> getAdminById(String id) {
        return adminRepo.findById(id);
    }

    public Admin saveAdmin(Admin admin) {
        return (Admin) usersServices.add(admin);
    }
    public void deleteAdmin(String id) {
        adminRepo.deleteById(id);
    }
}
