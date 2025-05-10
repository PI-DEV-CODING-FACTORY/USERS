package com.example.users.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private String profilePicture;
}