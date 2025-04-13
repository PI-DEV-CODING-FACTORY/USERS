package com.example.users.controllers;

import com.example.users.entities.User;
import com.example.users.services.UsersServices;
import lombok.RequiredArgsConstructor;
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
    public void add(@RequestBody User user){
        usersServices.add(user);
    }

    @GetMapping("/findById")
    public User findByEmail(@RequestParam String email){
        return usersServices.findByEmail(email);
    }

    @GetMapping("/findByRole")
    public List<User> findByRole(@RequestParam String role){
        return usersServices.findByRole(role);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user){
        return usersServices.update(user);
    }

    @DeleteMapping("/delete")
    public User deleteByEmail(@RequestParam String email){
        return usersServices.deleteByEmail(email);
    }

}
