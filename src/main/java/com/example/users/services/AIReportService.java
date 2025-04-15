//package com.example.users.services;//package com.example.users.services;
////
////import com.example.users.DTO.ReportAnalysis;
////import com.example.users.entities.Report;
////import com.example.users.entities.ReportStatus;
////import com.example.users.repositories.ReportRepo;
////import lombok.RequiredArgsConstructor;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.http.*;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////
////import java.util.ArrayList;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////
////@Service
////@RequiredArgsConstructor
////public class AIReportService {
////    @Value("${groq.api.key}")
////    private String groqApiKey;
////
////    @Value("${groq.api.url}")
////    private String groqApiUrl;
////
////    @Autowired
////    private final ReportService reportService;
////    @Autowired
////    private final ReportRepo reportRepo;
////    @Autowired
////    private final RestTemplate restTemplate;
////
////    List<Report> pendingReports = reportService.getPendingReports();
////    List<ReportAnalysis> reportAnalyses = new ArrayList<>();
////    for(Report report : pendingReports){
////        Map<String, String> requestBody = new HashMap<>();
////        requestBody.put("content", report.getDescription());
////        HttpHeaders headrers = new HttpHeaders();
////        headrers.setContentType(MediaType.APPLICATION_JSON);
////        headrers.set("Authorization", "Bearer " + groqApiKey);
////        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headrers);
////        try {
////            // Send the request to the Groq API
////            ResponseEntity<ReportAnalysis> response = restTemplate.postForEntity(groqApiUrl, requestEntity, ReportAnalysis.class);
////
////            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
////                ReportAnalysis analysis = response.getBody();
////                analysisResults.add(analysis);
////
////                // Update the report status and save the analysis result
////                report.setStatus(ReportStatus.Consulted);
////                report.setAnalysisResult(analysis.getResult()); // Assuming `Report` has a field for analysis result
////                reportRepo.save(report);
////            }
////        } catch (Exception e) {
////            // Handle API errors (e.g., log the error)
////            e.printStackTrace();
////        }
////    }
////
////}
//
//import com.example.users.DTO.ReportAnalysis;
//import com.example.users.entities.Report;
//import com.example.users.entities.ReportStatus;
//import com.example.users.repositories.ReportRepo;
//import com.example.users.services.ReportService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AIReportService {
//
//    @Value("${groq.api.key}")
//    private String groqApiKey;
//
//    @Value("${groq.api.url}")
//    private String groqApiUrl;
//
//    @Autowired
//    private final ReportService reportService;
//
//    @Autowired
//    private final ReportRepo reportRepo;
//
//    @Autowired
//    private final RestTemplate restTemplate;
//
//        // Fetch all pending reports
//        List<Report> pendingReports = reportService.getPendingReports();
//        List<ReportAnalysis> analysisResults = new ArrayList<>();
//
//}