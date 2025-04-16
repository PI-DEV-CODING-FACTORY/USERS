package com.example.users.services;

import com.example.users.entities.Entreprise;
import com.example.users.repositories.EntrepriseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntrepriseService {
    @Autowired
    private final EntrepriseRepo entrepriseRepo;

    public ResponseEntity<Entreprise> addEntreprise(Entreprise entreprise) {
        Entreprise savedEntreprise = entrepriseRepo.save(entreprise);
        return ResponseEntity.ok(savedEntreprise);
    }

    public ResponseEntity<Entreprise> getEntreprise(String id) {
        Entreprise entreprise = entrepriseRepo.findById(id).orElse(null);
        if (entreprise != null) {
            return ResponseEntity.ok(entreprise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Entreprise> updateEntreprise(String id, Entreprise entreprise) {
        if (entrepriseRepo.existsById(id)) {
            entreprise.setId(id);
            Entreprise updatedEntreprise = entrepriseRepo.save(entreprise);
            return ResponseEntity.ok(updatedEntreprise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteEntreprise(String id) {
        if (entrepriseRepo.existsById(id)) {
            entrepriseRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Entreprise> getEntrepriseByEmail(String email) {
        Entreprise entreprise = entrepriseRepo.findByEmail(email);
        if (entreprise != null) {
            return ResponseEntity.ok(entreprise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
