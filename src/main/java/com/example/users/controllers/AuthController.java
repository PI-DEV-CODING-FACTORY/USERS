package com.example.users.controllers;

import com.example.users.entities.User;
import com.example.users.services.JwtUtil;
import com.example.users.services.UsersServices;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    @Autowired
    UsersServices usersServices;
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
    @PostMapping("/reset-password")
    public boolean resetPassword(@RequestParam String email, @RequestParam String password){
        User user = usersServices.findByEmail(email);
        if(user != null){
            user.setPassword(passwordEncoder.encode(password));
            usersServices.update(user);
            return true;
        }
        return false;
    }
}
