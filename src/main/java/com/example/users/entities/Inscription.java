package com.example.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Inscription {
    @Id
            @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String firstName;
    String lastName;
    String personalEmail;
    Date dateOfBirth;
    String courseId;
    StatusInscri status;
    String bachelorDegree;
    String notesDocument;

    @Override
    public String toString() {
        return "Inscription{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", courseId='" + courseId + '\'' +
                ", status=" + status +
                '}';
    }
}
