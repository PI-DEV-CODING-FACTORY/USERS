package com.example.users.repositories;

import com.example.users.entities.User;
import com.example.users.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    List<User> findByRole(Role role);
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}
