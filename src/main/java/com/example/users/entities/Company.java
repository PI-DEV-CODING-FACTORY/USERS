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
@Table(name = "companies")
public class Company extends User {
    private String name;
    private String description;
    private String domain;
    @JsonIgnore
    private Date dateOfHire = new Date();

    @Override
    protected void onCreate() {
        super.onCreate();
        setRole(Role.COMPANY);
    }
}
