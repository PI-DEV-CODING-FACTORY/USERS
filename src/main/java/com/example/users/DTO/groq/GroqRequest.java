package com.example.users.DTO.groq;

import java.util.List;
import lombok.Data;

@Data
public class GroqRequest {
    private String model = "llama-3.3-70b-versatile"; // Default model
    private List<Message> messages;
    private int max_tokens = 1000;

    @Data
    public static class Message {
        private String role = "user";
        private String content;

        public Message(String content) {
            this.content = content;
        }
    }
}