package com.example.users.repositories;

import com.example.users.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepo extends JpaRepository<Entreprise,String> {
    Entreprise findByEmail(String email);
}
