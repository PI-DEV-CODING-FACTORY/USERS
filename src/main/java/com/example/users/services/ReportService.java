package com.example.users.services;

import com.example.users.entities.Report;
import com.example.users.entities.ReportStatus;
import com.example.users.repositories.ReportRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {
    @Autowired
    private final ReportRepo reportRepo;
    public void addReport(Report report){
        report.setStatus(ReportStatus.Pending);
        this.reportRepo.save(report);
    }
    public List<Report> getAllReports(){
        return this.reportRepo.findAll();
    }
    public void deleteReport(Long id) {
        this.reportRepo.deleteById(id);
    }
    public Report getReportById(Long id){
        return this.reportRepo.findById(id).orElse(null);
    }
//    public Report getReportByUserId(String id){
//        Report report = this.reportRepo.findByUserId(id);
//        if (report == null){
//            return null;
//        }else{
//            return report;
//        }
//        return this.reportRepo.findByUserId(id);
//    }
}
