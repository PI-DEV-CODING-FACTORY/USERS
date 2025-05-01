package com.example.users.entities;

import com.example.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    String email;
    String password;
    String firstname;
    String lastname;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    Role role;
    LocalDate dateOfBirth;
    @JsonIgnore
    Boolean isActive = false;
    @JsonIgnore
    Date CreatedAt;
    String profilePicture;
    @Lob
    byte[] degree;

    @PrePersist
    protected void onCreate() {
        if (CreatedAt == null) {
            CreatedAt = new Date();
        }
        if (isActive == null) {
            isActive = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        CreatedAt = new Date();
    }

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
                '}';
    }
}
