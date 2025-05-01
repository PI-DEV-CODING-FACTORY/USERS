package com.example.users.DTO;

import com.example.users.enums.StatusInscri;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
public class InscriptionRequest {
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String healthStatus;
    private String address;
    private String city;
    private Long zipCode;
    private String courseId;
    private StatusInscri status;
    private MultipartFile diplomaDocument;
}