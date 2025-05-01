package com.example.users.services;

import com.example.users.entities.Inscription;
import com.example.users.enums.Role;
import com.example.users.enums.StatusInscri;
import com.example.users.entities.User;
import com.example.users.repositories.InscriptionRepo;
import com.example.users.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InscriptionServices {
    @Autowired
    private InscriptionRepo inscriptionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Inscription add(Inscription inscription) throws IOException {
        return inscriptionRepo.save(inscription);
    }

//    @Transactional
    public List<Inscription> getAllInscriptions() {
        return inscriptionRepo.findAll();
    }

    public List<Inscription> getAllInscriptionsWithoutDocuments() {
        List<Inscription> inscriptions = inscriptionRepo.findAll();
        inscriptions.forEach(inscription -> inscription.setDiplomaDocument(null));
        return inscriptions;
    }
//    @Transactional
    public byte[] getDocumentById(String id) {
        Inscription inscription = inscriptionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscription not found with id: " + id));
        if (inscription.getDiplomaDocument() == null) {
            throw new IllegalArgumentException("No document found for the given inscription id: " + id);
        }
        return inscription.getDiplomaDocument();
    }

    public Inscription getInscriptionById(String id) {
        return inscriptionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscription not found with id: " + id));
    }

    public List<Inscription> getPendingInscriptions() {
        return inscriptionRepo.findByStatus(StatusInscri.Pending);
    }

    public List<Inscription> getAcceptedInscriptions() {
        return inscriptionRepo.findByStatus(StatusInscri.Approved);
    }

    public List<Inscription> getRejectedInscriptions() {
        return inscriptionRepo.findByStatus(StatusInscri.Rejected);
    }

    public void approveInscription(String inscriptionId) {
        // Find the inscription by ID
        Inscription inscription = inscriptionRepo.findById(inscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscription not found with id: " + inscriptionId));

        // Update the status to Approved
        inscription.setStatus(StatusInscri.Approved);
        inscriptionRepo.save(inscription);

        // Create a new user with the role "Student"
        User newUser = new User();
        newUser.setEmail(inscription.getPersonalEmail());
        newUser.setPassword(passwordEncoder.encode("defaultPassword")); // Set a default password
        newUser.setFirstname(inscription.getFirstName());
        newUser.setLastname(inscription.getLastName());
        newUser.setDateOfBirth(inscription.getDateOfBirth());
        newUser.setRole(Role.STUDENT);
        newUser.setIsActive(true);
        userRepo.save(newUser);
    }
}