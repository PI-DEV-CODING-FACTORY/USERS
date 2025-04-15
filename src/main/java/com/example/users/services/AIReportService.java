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

    public Report analyzeReport(Long reportId) {
        Report report = reportRepo.findById(reportId).orElseThrow(
                () -> new RuntimeException("Report not found with id: " + reportId)
        );

        if (report.getStatus() != ReportStatus.Pending) {
            throw new RuntimeException("Report is already analyzed or in progress");
        }

        try {
            // Update status to analyzing
            report.setStatus(ReportStatus.ANALYZING);
            reportRepo.save(report);

            // Prepare the prompt
            String prompt = String.format(
                    "Analyze the following technical report and provide a structured JSON response containing:\\n\" +\n" +
                            "        \"1. A concise summary (3-5 sentences)\\n\" +\n" +
                            "        \"2. Priority assessment (Low/Medium/High/Critical) with justification\\n\" +\n" +
                            "        \"3. 3-5 suggested solutions or action items\\n\" +\n" +
                            "        \"4. Potential risks if not addressed\\n\\n\" +\n" +
                            "        \"Report Title: %s\\n\" +\n" +
                            "        \"Report Description: %s\\n\\n\" +\n" +
                            "        \"Respond ONLY with valid JSON in this exact structure:\\n\" +\n" +
                            "        \"{\\n\" +\n" +
                            "        \"  \\\"summary\\\": \\\"string\\\",\\n\" +\n" +
                            "        \"  \\\"priority\\\": \\\"enum[Low,Medium,High,Critical]\\\",\\n\" +\n" +
                            "        \"  \\\"priority_reason\\\": \\\"string\\\",\\n\" +\n" +
                            "        \"  \\\"solutions\\\": [\\\"string1\\\", \\\"string2\\\", \\\"string3\\\"],\\n\" +\n" +
                            "        \"  \\\"risks\\\": \\\"string\\\"\\n\" +\n" +
                            "        \"}\\n\\n\" +\n" +
                            "        \"Keep the entire response parsable as JSON. Do not include any explanatory text outside the JSON structure.",
                    report.getTitle(), report.getDescription()
            );

            // Call Groq API
            String analysis = callGroqApi(prompt);

            // Update report
            report.setReportAnalysis(analysis);
            report.setStatus(ReportStatus.ANALYZED);
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
        return reportRepo.findByStatus(ReportStatus.ANALYZED);
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