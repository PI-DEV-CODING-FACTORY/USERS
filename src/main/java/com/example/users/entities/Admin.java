package com.example.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
