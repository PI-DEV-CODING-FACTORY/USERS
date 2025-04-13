package com.example.users.controllers;

import com.example.users.entities.CustomResponse;
import com.example.users.entities.PasswordResetToken;
import com.example.users.entities.User;
import com.example.users.repositories.PasswordTokenRepo;
import com.example.users.services.JwtUtil;
import com.example.users.services.ResetPasswordTokenService;
import com.example.users.services.UsersServices;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    PasswordTokenRepo passwordTokenRepo;
    @Autowired
    UsersServices usersServices;
    @Autowired
    ResetPasswordTokenService resetPasswordTokenService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password){
        User user = usersServices.findByEmail(email);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return jwtUtil.generateToken(user.getEmail());
        }
        return "invalid credentials";
    }
    @PostMapping("/forgot-password")
    public CustomResponse<?> forgotPassword(@RequestParam String email) {
        User user = usersServices.findByEmail(email);
        if (user == null) {
            return new CustomResponse<>("USER_NOT_FOUND", "User does not exist.");
        }

        PasswordResetToken existingToken = passwordTokenRepo.findByUser(user);
        if (existingToken != null) {
            if (existingToken.getExpiryDate().isAfter(LocalDateTime.now().minusMinutes(5))) {
                // Token exists and was sent within the last 5 minutes
                return new CustomResponse<>("TOKEN_ALREADY_SENT", "A reset token was already sent. Please check your email.");
            } else {
                // Token exists but is expired, delete it
                passwordTokenRepo.delete(existingToken);
            }
        }

        // Generate a new token
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        resetPasswordTokenService.saveToken(passwordResetToken);
        return new CustomResponse<>("TOKEN_SENT", "Reset token sent successfully. Check your email.");
    }
    @PostMapping("/reset-password")
    public CustomResponse<?> resetPassword(@RequestBody User user) {
        User updatedUser = usersServices.update(user);
        if (updatedUser != null) {
            return new CustomResponse<>("SUCCESS", "Password updated successfully.");
        }
        return new CustomResponse<>("FAILURE", "Failed to update password.");
    }
    @GetMapping("/user")
    public User getUser(@RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);
        String email = jwtUtil.extractUsername(jwtToken);
        return usersServices.findByEmail(email);
    }
    @PostMapping("/validateToken")
    public CustomResponse<?> validateToken(@RequestParam String token) {
        User user = resetPasswordTokenService.validateToken(token);
        if (user != null) {
            return new CustomResponse<>("VALID", "Token is valid.", user);
        } else {
            return new CustomResponse<>("INVALID", "Token is invalid.");
        }
    }
}
