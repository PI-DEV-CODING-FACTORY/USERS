package com.example.users.entities;

import com.example.users.enums.StatusInscri;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    String phoneNumber;
    LocalDate dateOfBirth;
    String maritalStatus;
    String HealthStatus;
    String address;
    String city;
    Long zipCode;

    @Lob
    private byte[] diplomaDocument;
    private String diplomaDocumentType;
    private String diplomaDocumentName;

    String courseId;
    @Enumerated(EnumType.STRING)
    StatusInscri status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Override
    public String toString() {
        return "Inscription{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", situation='" + maritalStatus + '\'' +
                ", HealthSituation='" + HealthStatus + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                ", courseId='" + courseId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}