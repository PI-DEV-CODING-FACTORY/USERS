package com.example.users.controllers;

import com.example.users.entities.Inscription;
import com.example.users.entities.StatusInscri;
import com.example.users.services.InscriptionServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/inscription")
public class InscriptionController {

    private final InscriptionServices inscriptionServices;

//    @PostMapping( "/add")
//    public ResponseEntity<Inscription> createInscription(
//           @RequestBody Inscription inscription
//    ) throws IOException {
//        Inscription savedInscription = inscriptionServices.add(inscription);
//        return ResponseEntity.ok(savedInscription);
//    }

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Inscription> createInscription(
            @RequestPart("file") MultipartFile file,
            @RequestPart("inscription") String inscriptionJson
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Inscription inscription = objectMapper.readValue(inscriptionJson, Inscription.class);
        inscription.setDiplomaDocument(file.getBytes());
        inscription.setDiplomaDocumentType(file.getContentType());
        inscription.setDiplomaDocumentName(file.getOriginalFilename());

        Inscription savedInscription = inscriptionServices.add(inscription);
        return ResponseEntity.ok(savedInscription);
    }

    @GetMapping("/all")
    public List<Inscription> getAllInscriptions() {
        return inscriptionServices.getAllInscriptions();
    }

    @GetMapping("/pending")
    public List<Inscription> getPendingInscriptions() {
        return inscriptionServices.getPendingInscriptions();
    }

    @GetMapping("/accepted")
    public List<Inscription> getAcceptedInscriptions() {
        return inscriptionServices.getAcceptedInscriptions();
    }

    @GetMapping("/rejected")
    public List<Inscription> getRejectedInscriptions() {
        return inscriptionServices.getRejectedInscriptions();
    }
}