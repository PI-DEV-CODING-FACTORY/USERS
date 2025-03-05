package com.example.users.repositories;

import com.example.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    List<User> findByRole(String role);


    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);


}
