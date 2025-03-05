package com.example.users.repositories;

import com.example.users.entities.Inscription;
import com.example.users.entities.StatusInscri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InscriptionRepo extends JpaRepository<Inscription,String> {
    // create a query that allow to display the inscriptions by status
    @Query("SELECT i FROM Inscription i WHERE i.status = ?1")
    List<Inscription> findByStatus(StatusInscri status);
}
