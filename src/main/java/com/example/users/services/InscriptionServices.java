package com.example.users.services;

import com.example.users.entities.Inscription;
import com.example.users.entities.Role;
import com.example.users.entities.StatusInscri;
import com.example.users.entities.User;
import com.example.users.repositories.InscriptionRepo;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InscriptionServices {
    @Autowired
    private InscriptionRepo inscriptionRepo;
    @Autowired
    UsersServices usersServices;
    @Autowired
    private JavaMailSender mailSender;

    public List<Inscription> getAllInscriptions(){
        return inscriptionRepo.findAll();
    }
    public void add(Inscription inscription, MultipartFile bachelorDegree, MultipartFile notesDocument) throws IOException {
        if (bachelorDegree != null) {
            inscription.setDiplomaDocument(bachelorDegree.getBytes());
        }
        if (notesDocument != null) {
            inscription.setNotesDocument(notesDocument.getBytes());
        }
        inscriptionRepo.save(inscription);
    }
    // get the pending inscriptions
    public List<Inscription> getPendingInscriptions(){
        return inscriptionRepo.findByStatus(StatusInscri.Pending);
    }
    // get the accepted inscriptions
    public List<Inscription> getAcceptedInscriptions(){
        return inscriptionRepo.findByStatus(StatusInscri.Approved);
    }
    // get the rejected inscriptions
    public List<Inscription> getRejectedInscriptions(){
        return inscriptionRepo.findByStatus(StatusInscri.Rejected);
    }
    public Boolean acceptInscription(String id){
        Inscription inscription = inscriptionRepo.findById(id).orElse(null);
        if(inscription != null){
            inscription.setStatus(StatusInscri.Approved);
            User newUser = new User();
            String password = generatePassword();
            newUser.setEmail(inscription.getFirstName()+"."+inscription.getLastName()+"@coding-factory.com");
            newUser.setFirstname(inscription.getFirstName());
            newUser.setLastname(inscription.getLastName());
            newUser.setPassword(password);
            newUser.setRole(Role.STUDENT);
            newUser.setCreatedAt(new Date());
            newUser.setDateOfBirth(inscription.getDateOfBirth());
            usersServices.add(newUser);
            inscriptionRepo.save(inscription);
            sendCredential(inscription.getPersonalEmail(), newUser.getEmail(), password);
            return true;
        }
        return false;
    }
    public Boolean rejectInscription(String id){
        Inscription inscription = inscriptionRepo.findById(id).orElse(null);
        if(inscription != null){
            inscription.setStatus(StatusInscri.Rejected);
            inscriptionRepo.save(inscription);
            return true;
        }
        return false;
    }
    public String generatePassword(){
        String password = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for(int i = 0; i < 8; i++){
            int index = (int) (Math.random() * characters.length());
            password += characters.charAt(index);
        }
        return password;
    }
    public void sendCredential(String personalEmail, String accountEmail, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(personalEmail);
        message.setSubject("Your Coding Factory Account Credentials");
        message.setText("Welcome to Coding Factory!\n\n" +
                "Your account has been created. Here are your credentials:\n\n" +
                "Email: " + accountEmail + "\n" +
                "Password: " + password + "\n\n" +
                "Please change your password after your first login.");
        System.out.println(" personal email:"+personalEmail+"sending ...");

        mailSender.send(message);
        System.out.println("Email sent successfully!");
    }

    public Optional<Inscription> getInscriptionById(String id) {
        return inscriptionRepo.findById(id);
    }
}
