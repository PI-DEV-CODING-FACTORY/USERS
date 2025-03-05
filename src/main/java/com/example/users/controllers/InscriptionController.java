package com.example.users.controllers;


import com.example.users.entities.Inscription;
import com.example.users.services.InscriptionServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("inscription")
public class InscriptionController {
    private final InscriptionServices inscriptionServices;

    @PostMapping("/add")
    public void add(@RequestBody Inscription inscription){
        inscriptionServices.add(inscription);
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
}
