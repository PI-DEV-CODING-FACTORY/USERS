package com.example.users.entities;

import com.example.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

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
    private Date dateOfHire;
    private boolean responsibleDepartement = false;

    @Override
    protected void onCreate() {
        super.onCreate();
        setRole(Role.TEACHER);
    }
}
