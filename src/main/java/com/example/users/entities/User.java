package com.example.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
    Role role;
    Date dateOfBirth;
    Boolean isActive;
    Date CreatedAt;
    String profilePicture;
    String bachelordegree;
    String notesDocument;

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
                '}';
    }
}
