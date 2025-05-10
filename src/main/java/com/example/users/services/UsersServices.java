package com.example.users.services;

import com.example.users.entities.User;
import com.example.users.enums.Role;
import com.example.users.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersServices {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public User add(User user){
        String HashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(HashedPassword);
        user.setIsActive(true);
        user.setCreatedAt(new java.util.Date());
        return userRepo.save(user);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User update(User user){
        if (userRepo.findByEmail(user.getEmail()) == null){
            return null;
        }else{
            String HashedPassword = passwordEncoder.encode( user.getPassword());
            user.setPassword(HashedPassword);
            return userRepo.save(user);
        }
    }
    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public List<User> findByRole(Role role){
        return userRepo.findByRole(role);
    }
    public User deleteByEmail(String email){
        User user = userRepo.findByEmail(email);
        if(user != null){
            userRepo.delete(user);
            return user;
        }
        return null;
    }
}
