package com.example.users.entities;

import com.example.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher extends User {
    private String numTeacher;
    private String subject;
    private String department;
    private Integer experienceYears;
    @JsonIgnore
    private Date dateOfHire = new Date();
    private boolean responsibleDepartement = false;

    @Override
    protected void onCreate() {
        super.onCreate();
        setRole(Role.TEACHER);
    }
}
