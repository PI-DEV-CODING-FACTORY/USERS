package com.example.users.services;

import com.example.users.entities.Inscription;
import com.example.users.entities.StatusInscri;
import com.example.users.repositories.InscriptionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InscriptionServices {
    @Autowired
    private InscriptionRepo inscriptionRepo;

    public Inscription add(Inscription inscription) throws IOException {
//        if (diplomaDocument != null && !diplomaDocument.isEmpty()) {
//            inscription.setDiplomaDocument(diplomaDocument.getBytes());
//            inscription.setDiplomaDocumentType(diplomaDocument.getContentType());
//        }
        if (inscription.getStatus() == null) {
            inscription.setStatus(StatusInscri.Pending);
        }
        return inscriptionRepo.save(inscription);
    }

    public List<Inscription> getAllInscriptions() {
        return inscriptionRepo.findAll();
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
}