package com.example.users.entities;

import com.example.users.enums.Role;
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
    private String promotion;
    private String filiere;
    private Integer annee;
    private String numeroEtudiant;
    private Double moyenneGenerale;
    private Boolean boursier = false;

    @Override
    protected void onCreate() {
        super.onCreate();
        setRole(Role.STUDENT);
    }
}
