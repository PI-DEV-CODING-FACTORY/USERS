package com.example.users.controllers;

import com.example.users.entities.User;
import com.example.users.enums.Role;
import com.example.users.services.UsersServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersServices usersServices;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        List<User> users = usersServices.getAllUsers();
        return users;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        usersServices.add(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findById")
    public User findByEmail(@RequestParam String email){
        return usersServices.findByEmail(email);
    }

    @GetMapping("/findByRole")
    public List<User> findByRole(@RequestParam Role role){
        return usersServices.findByRole(role);
    }

    @DeleteMapping("/delete")
    public User deleteByEmail(@RequestParam String email){
        return usersServices.deleteByEmail(email);
    }

}
