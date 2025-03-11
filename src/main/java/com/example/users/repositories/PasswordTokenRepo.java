package com.example.users.repositories;

import com.example.users.entities.PasswordResetToken;
import com.example.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    // query to get the token by the user
    @Query("SELECT p FROM PasswordResetToken p WHERE p.user.email = :email")
    PasswordResetToken findByUserEmail(@Param("email") String email);
}
