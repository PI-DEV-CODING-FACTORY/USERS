package com.example.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {
    @Id
    String email;
    String password;
    String firstname;
    String lastname;
    @Enumerated(EnumType.STRING)
    Role role;
    Date dateOfBirth;
    @JsonIgnore
    Boolean isActive = false;
    @JsonIgnore
    Date CreatedAt;
    String profilePicture;
    @Lob
    byte[] degree;


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                ", dateOfBirth=" + dateOfBirth +
                ", isActive=" + isActive +
                ", CreatedAt=" + CreatedAt +
                ", profilePicture='" + profilePicture + '\'' +
                ", degree=" + Arrays.toString(degree) +
                '}';
    }
}
