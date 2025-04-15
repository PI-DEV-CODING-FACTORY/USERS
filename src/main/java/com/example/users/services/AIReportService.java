package com.example.users.services;

import com.example.users.DTO.groq.GroqRequest;
import com.example.users.DTO.groq.GroqResponse;
import com.example.users.entities.Report;
import com.example.users.entities.ReportStatus;
import com.example.users.repositories.ReportRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIReportService {
    private final ReportRepo reportRepo;
    private final RestTemplate restTemplate;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.api.url}")
    private String groqApiUrl;

//    private final String promp = String.format()

    public Report analyzeReport(Long reportId) {
        Report report = reportRepo.findById(reportId).orElseThrow(
                () -> new RuntimeException("Report not found with id: " + reportId)
        );

        if (report.getStatus() != ReportStatus.Pending) {
            throw new RuntimeException("Report is already analyzed or in progress");
        }

        try {
            // Update status to analyzing
//            report.setStatus(ReportStatus.ANALYZING);
            reportRepo.save(report);

            // Prepare the prompt
            String prompt = String.format(
                    "Analysez le rapport technique suivant et fournissez une réponse JSON structurée en français contenant :\\n\" +\n" +
                            "        \"1. Un résumé concis (3-5 phrases)\\n\" +\n" +
                            "        \"2. Une priorité numérique (1 à 5, 1 étant le plus urgent)\\n\" +\n" +
                            "        \"3. 3 solutions proposées\\n\" +\n" +
                            "        \"4. Les risques potentiels\\n\\n\" +\n" +
                            "        \"Titre du Rapport: %s\\n\" +\n" +
                            "        \"Description du Rapport: %s\\n\\n\" +\n" +
                            "        \"Répondez UNIQUEMENT avec un JSON valide dans ce format exact :\\n\" +\n" +
                            "        \"{\\n\" +\n" +
                            "        \"  \\\"summary\\\": \\\"résumé\\\",\\n\" +\n" +
                            "        \"  \\\"priority\\\": 1-5,\\n\" +\n" +
                            "        \"  \\\"solutions\\\": [\\\"solution 1\\\", \\\"solution 2\\\", \\\"solution 3\\\"],\\n\" +\n" +
                            "        \"  \\\"risks\\\": \\\"risques\\\"\\n\" +\n" +
                            "        \"}\\n\\n\" +\n" +
                            "        \"Priorités :\\n\" +\n" +
                            "        \"1 - Critique/Urgent\\n\" +\n" +
                            "        \"2 - Haut\\n\" +\n" +
                            "        \"3 - Moyen\\n\" +\n" +
                            "        \"4 - Bas\\n\" +\n" +
                            "        \"5 - Très bas/Négligeable\\n\\n\" +\n" +
                            "        \"N'incluez aucun texte explicatif en dehors de la structure JSON.",
                    report.getTitle(), report.getDescription()
            );

            // Call Groq API
            String analysis = callGroqApi(prompt);

            // Update report
            report.setReportAnalysis(analysis);
            report.setAiAnalyzed(true);
//            report.setStatus(ReportStatus.ANALYZED);
            return reportRepo.save(report);
        } catch (Exception e) {
            log.error("Error analyzing report {}: {}", reportId, e.getMessage());
            report.setStatus(ReportStatus.Pending);
            reportRepo.save(report);
            throw new RuntimeException("Failed to analyze report", e);
        }
    }

    public List<Report> analyzeAllPendingReports() {
        List<Report> pendingReports = reportRepo.findByStatus(ReportStatus.Pending);
        pendingReports.forEach(report -> {
            try {
                analyzeReport(report.getId());
            } catch (Exception e) {
                log.error("Failed to analyze report {}: {}", report.getId(), e.getMessage());
            }
        });
        return reportRepo.findByAiAnalyzed(true);
//        return reportRepo.findByStatus(ReportStatus.ANALYZED);
    }

    private String callGroqApi(String prompt) {
        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + groqApiKey);

            // Prepare request body
            GroqRequest.Message message = new GroqRequest.Message(prompt);
            GroqRequest request = new GroqRequest();
            request.setMessages(Collections.singletonList(message));

            // Make API call
            HttpEntity<GroqRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<GroqResponse> response = restTemplate.exchange(
                    groqApiUrl,
                    HttpMethod.POST,
                    entity,
                    GroqResponse.class
            );

            // Process response
            if (response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    !response.getBody().getChoices().isEmpty()) {
                return response.getBody().getChoices().get(0).getMessage().getContent();
            }
            throw new RuntimeException("Empty or invalid response from Groq API");
        } catch (Exception e) {
            log.error("Groq API call failed: {}", e.getMessage());
            throw new RuntimeException("Failed to call Groq API", e);
        }
    }
}