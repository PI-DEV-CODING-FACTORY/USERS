package com.example.users.controllers;

import com.example.users.entities.Entreprise;
import com.example.users.services.EntrepriseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/entreprise")
public class EntrepriseController {
    @Autowired
    private final EntrepriseService entrepriseService;

    @PostMapping("/add")
    public ResponseEntity<Entreprise> addEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseService.addEntreprise(entreprise);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable String id) {
        return entrepriseService.getEntreprise(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable String id, @RequestBody Entreprise entreprise) {
        return entrepriseService.updateEntreprise(id, entreprise);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable String id) {
        return entrepriseService.deleteEntreprise(id);
    }
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Entreprise> getEntrepriseByEmail(@PathVariable String email) {
        return entrepriseService.getEntrepriseByEmail(email);
    }
}
