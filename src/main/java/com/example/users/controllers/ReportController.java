package com.example.users.controllers;

import com.example.users.entities.Report;
import com.example.users.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("report")
public class ReportController {
    @Autowired
    private final ReportService reportService;
    //create a report endpoint
    @PostMapping("/add")
    public void addReport(@RequestBody Report report){
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
