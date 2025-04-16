package com.example.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
            @JsonIgnore
    String id;
    String name;
    String email;
    String password;
    String phoneNumber;
    String address;

    @Override
    public String toString() {
        return "entreprise{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

