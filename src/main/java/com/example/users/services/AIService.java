package com.example.users.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AIService {
//    private final OkHttpClient client = new OkHttpClient();
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Value("${groq.api.key}")
//    private String apiKey;
//
//    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";
//
//    public String analyzeReport(String reportContent) {
//        try {
//            String prompt = String.format(
//                    "Please analyze this report: %s\n" +
//                            "Provide a concise summary and assign a priority from 1-4 where:" +
//                            "4: Critical/Urgent, " +
//                            "3: High priority, " +
//                            "2: Medium priority, " +
//                            "1: Low priority. " +
//                            "Format response as: Priority: [number]\nSummary: [brief summary]",
//                    reportContent
//            );
//
//            String jsonBody = mapper.writeValueAsString(new ChatRequest(
//                    "llama-3.3-70b-versatile",
//                    new Message[]{new Message("user", prompt)}
//            ));
//
//            Request request = new Request.Builder()
//                    .url(GROQ_API_URL)
//                    .addHeader("Authorization", "Bearer " + apiKey)
//                    .addHeader("Content-Type", "application/json")
//                    .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
//                    .build();
//
//            try (Response response = client.newCall(request).execute()) {
//                if (!response.isSuccessful()) {
//                    throw new RuntimeException("API call failed: " + response.code());
//                }
//                JsonNode jsonResponse = mapper.readTree(response.body().string());
//                return jsonResponse.path("choices").get(0).path("message").path("content").asText();
//            }
//        } catch (Exception e) {
//            log.error("Error analyzing report", e);
//            return "Priority: 1\nSummary: Error analyzing report content";
//        }
//    }
//
//    private record ChatRequest(String model, Message[] messages) {}
//    private record Message(String role, String content) {}
}
