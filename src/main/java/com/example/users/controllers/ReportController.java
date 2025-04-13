package com.example.users.controllers;

import com.example.users.entities.Report;
import com.example.users.entities.ReportStatus;
import com.example.users.services.JwtUtil;
import com.example.users.services.ReportService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private final ReportService reportService;
    @Autowired
    private final JwtUtil jwtUtil;
    //create a report endpoint
    @PostMapping("/add")
    public void addReport(@RequestBody Report report ,@RequestHeader("Authorization") String authorizationHeader){
        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Print the token to the console
        System.out.println("JWT Token: " + token);
        // Parse the JWT token to extract claims
        String email = jwtUtil.extractEmail(token);

        System.out.println("Email from JWT: " + email);
        report.setUserId(email);
        report.setStatus(ReportStatus.Pending);
        report.setDateTime(LocalDateTime.now());
        reportService.addReport(report);
    }
    //get all reports endpoint
    @GetMapping("/all")
    public List<Report> getAllReports(){
        return reportService.getAllReports();
    }
    //delete a report endpoint
    @DeleteMapping("/delete/{id}")
    public void deleteReport(@PathVariable Long id){
        reportService.deleteReport(id);
    }
    //get a report by id endpoint
    @GetMapping("/get/{id}")
    public Report getReportById(@PathVariable Long id){
        return reportService.getReportById(id);
    }
}
