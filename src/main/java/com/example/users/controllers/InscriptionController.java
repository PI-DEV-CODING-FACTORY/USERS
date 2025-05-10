package com.example.users.controllers;

import com.example.users.DTO.InscriptionRequest;
import com.example.users.entities.Inscription;
import com.example.users.enums.StatusInscri;
import com.example.users.services.InscriptionServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/inscription")
public class InscriptionController {

    private final InscriptionServices inscriptionServices;

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Inscription> createInscription(
            @ModelAttribute InscriptionRequest request
            ) throws IOException {

        if (request.getDiplomaDocument() == null || request.getDiplomaDocument().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diploma document is required");
        }

        Inscription inscription = new Inscription();
        inscription.setFirstName(request.getFirstName());
        inscription.setLastName(request.getLastName());
        inscription.setPersonalEmail(request.getPersonalEmail());
        inscription.setPhoneNumber(request.getPhoneNumber());
        inscription.setDateOfBirth(request.getDateOfBirth());
        inscription.setMaritalStatus(request.getMaritalStatus());
        inscription.setHealthStatus(request.getHealthStatus());
        inscription.setAddress(request.getAddress());
        inscription.setCity(request.getCity());
        inscription.setZipCode(request.getZipCode());
        inscription.setCourseId(request.getCourseId());
        inscription.setStatus(StatusInscri.Pending);
        inscription.setCreatedAt(new java.util.Date());

        MultipartFile file = request.getDiplomaDocument();
        inscription.setDiplomaDocument(file.getBytes());
        inscription.setDiplomaDocumentType(file.getContentType());
        inscription.setDiplomaDocumentName(file.getOriginalFilename());
        Inscription savedInscription = inscriptionServices.add(inscription);
        return ResponseEntity.ok(savedInscription);
    }

    @GetMapping("/all")
    public List<Inscription> getAllInscriptions() {
        return inscriptionServices.getAllInscriptionsWithoutDocuments();
    }

    @GetMapping("/allWithDocuments")
    public List<Inscription> getAllInscriptionsWithDocuments() {
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

    @GetMapping("/{id}/document")
    public ResponseEntity<byte[]> getDocumentById(@PathVariable String id) {
        byte[] document = inscriptionServices.getDocumentById(id);
        Inscription inscription = inscriptionServices.getInscriptionById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(inscription.getDiplomaDocumentType()));
        headers.setContentDisposition(
                ContentDisposition.builder("inline").filename(inscription.getDiplomaDocumentName()).build()
        );
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveInscription(@PathVariable String id) {
        try {
            inscriptionServices.approveInscription(id);
            return ResponseEntity.ok("Inscription approved and user created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectInscription(@PathVariable String id) {
        try {
            inscriptionServices.rejectInscription(id);
            return ResponseEntity.ok("Inscription rejected successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}