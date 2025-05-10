package com.example.users.entities;

import com.example.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Student extends User {
    @JsonIgnore
    private String numeroEtudiant;
    private String personalEmail;
    private String promotion;
    private String filiere;
    private Integer annee;
    private Double moyenneGenerale;
    private Boolean boursier = false;
    private String maritalStatus;
    private String HealthStatus;

    @Override
    protected void onCreate() {
        super.onCreate();
        setRole(Role.STUDENT);
    }
}
