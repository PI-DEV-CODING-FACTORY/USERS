package com.example.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.Arrays;
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
    Date dateOfBirth;
    String situation;
    String HealthSituation;
    String address;
    String city;
    Long zipCode;
    String lastDiploma;
    @JsonIgnore
    @Lob
    byte[] diplomaDocument;
    @JsonIgnore
    String diplomaDocumentType;
    @JsonIgnore
    @Lob
    byte[] notesDocument;
    @JsonIgnore
    String notesDocumentType;

    String courseId;
    @Enumerated(EnumType.STRING)
    StatusInscri status;

    @Override
    public String toString() {
        return "Inscription{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", situation='" + situation + '\'' +
                ", HealthSituation='" + HealthSituation + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                ", lastDiploma='" + lastDiploma + '\'' +
                ", diplomaDocument=" + Arrays.toString(diplomaDocument) +
                ", diplomaDocumentType='" + diplomaDocumentType + '\'' +
                ", notesDocument=" + Arrays.toString(notesDocument) +
                ", notesDocumentType='" + notesDocumentType + '\'' +
                ", courseId='" + courseId + '\'' +
                ", status=" + status +
                '}';
    }
}
