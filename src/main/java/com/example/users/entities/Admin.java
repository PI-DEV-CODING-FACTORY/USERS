package com.example.users.entities;

import com.example.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "admins")
public class Admin extends User{
    private String numAdmin;
    private String service;
    private String department;
    private Integer experienceYears;
    @JsonIgnore
    private Date dateOfHire = new Date();
    private boolean responsibleDepartement = false;
    private boolean fullAccess = false;

    @Override
    protected void onCreate() {
        super.onCreate();
        setRole(Role.ADMIN);
    }
}
