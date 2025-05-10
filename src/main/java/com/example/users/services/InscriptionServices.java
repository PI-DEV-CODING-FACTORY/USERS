package com.example.users.services;

import com.example.users.entities.Inscription;
import com.example.users.entities.Student;
import com.example.users.enums.StatusInscri;
import com.example.users.entities.User;
import com.example.users.repositories.InscriptionRepo;
import com.example.users.repositories.StudentRepo;
import com.example.users.repositories.UserRepo;
import com.example.users.util.EmailUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InscriptionServices {
    @Autowired
    private InscriptionRepo inscriptionRepo;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailUtil emailUtil;

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

    public String passwordGenerator(){
        StringBuilder password = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        int length = 10; // Length of the password
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
    public void approveInscription(String inscriptionId) {
        // Find the inscription by ID
        Inscription inscription = inscriptionRepo.findById(inscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscription not found with id: " + inscriptionId));

        // Update the status to Approved
        inscription.setStatus(StatusInscri.Approved);
        inscriptionRepo.save(inscription);

        // Create a new student

        Student student = new Student();
        student.setPersonalEmail(inscription.getPersonalEmail());
        student.setPromotion(inscription.getCourseId());
        student.setFiliere(inscription.getCourseId());
        student.setAnnee(LocalDate.now().getYear());
        student.setMaritalStatus(inscription.getMaritalStatus());
        student.setHealthStatus(inscription.getHealthStatus());
        student.setEmail(inscription.getFirstName()+'.'+inscription.getLastName()+ "@codingFactory.com");
        String password = passwordGenerator();
        student.setPassword(password);
        student.setFirstname(inscription.getFirstName());
        student.setLastname(inscription.getLastName());
        student.setDateOfBirth(inscription.getDateOfBirth());
        student.setDegree(inscription.getDiplomaDocument());
        Student studentTOLog = studentService.saveStudent(student);
        System.out.println("Student created: " + studentTOLog);
        emailUtil.sendEmail(student.getPersonalEmail(),
                "Inscription Approved",
                "Dear " + student.getFirstname() + " " + student.getLastname() + ",\n\n" +
                        "Congratulations! Your inscription has been approved.\n" +
                        "Your email: " + student.getEmail() + "\n" +
                        "Your password: " + password + "\n\n" +
                        "Best regards,\n" +
                        "The Coding Factory Team");
    }
    public void rejectInscription(String inscriptionId) {
        // Find the inscription by ID
        Inscription inscription = inscriptionRepo.findById(inscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscription not found with id: " + inscriptionId));

        // Update the status to Rejected
        inscription.setStatus(StatusInscri.Rejected);
        inscriptionRepo.save(inscription);
        emailUtil.sendEmail(inscription.getPersonalEmail(),
                "Inscription Rejected",
                "Dear " + inscription.getFirstName() + " " + inscription.getLastName() + ",\n\n" +
                        "Your inscription has been rejected.\n\n" +
                        "Best regards,\n" +
                        "The Coding Factory Team");
    }
}