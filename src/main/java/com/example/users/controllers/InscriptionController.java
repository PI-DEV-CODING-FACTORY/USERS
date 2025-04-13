package com.example.users.controllers;


import com.example.users.entities.Inscription;
import com.example.users.entities.StatusInscri;
import com.example.users.services.InscriptionServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/inscription")
public class InscriptionController {
    private final InscriptionServices inscriptionServices;

    @PostMapping(
            value = "/add",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void add(
            @RequestPart Inscription inscription,
            @RequestPart(value = "diplomaDocument", required = false) MultipartFile diplomaDocument,
            @RequestPart(value = "notesDocument", required = false) MultipartFile notesDocument
    ) throws IOException {
        if (diplomaDocument != null) {
            inscription.setDiplomaDocument(diplomaDocument.getBytes());
            inscription.setDiplomaDocumentType(diplomaDocument.getContentType());
        }
        if (notesDocument != null) {
            inscription.setNotesDocument(notesDocument.getBytes());
            inscription.setNotesDocumentType(notesDocument.getContentType());
        }
        inscription.setStatus(StatusInscri.Pending);
        inscriptionServices.add(inscription, diplomaDocument, notesDocument);
    }
    @GetMapping("/all")
    public List<Inscription> getAllInscriptions(){
        return inscriptionServices.getAllInscriptions();
    }
    @GetMapping("/pending")
    public List<Inscription> getPendingInscriptions(){
        return inscriptionServices.getPendingInscriptions();
    }
    @GetMapping("/accepted")
    public List<Inscription> getAcceptedInscriptions(){
        return inscriptionServices.getAcceptedInscriptions();
    }
    @GetMapping("/rejected")
    public List<Inscription> getRejectedInscriptions(){
        return inscriptionServices.getRejectedInscriptions();
    }
    @PutMapping("/accept/{id}")
    public void acceptInscription(@PathVariable String id){
        inscriptionServices.acceptInscription(id);
    }
    @PutMapping("/reject/{id}")
    public void rejectInscription(@PathVariable String id){
        inscriptionServices.rejectInscription(id);
    }

//    @GetMapping("/{id}/bachelor-degree")
//    public ResponseEntity<byte[]> downloadBachelorDegree(@PathVariable String id) {
//        Optional<Inscription> optionalInscription = inscriptionServices.getInscriptionById(id);
//        if (optionalInscription.isEmpty() || optionalInscription.get().getBachelorDegree() == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bachelor_degree.pdf")
//                .body(optionalInscription.get().getBachelorDegree());
//    }
}
