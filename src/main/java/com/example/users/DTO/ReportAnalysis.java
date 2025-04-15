package com.example.users.DTO;

import java.util.List;

public class ReportAnalysis {
    private String originalContent;
    private String summary;
    private String priority;
    private List<String> suggestedSolutions;

    public ReportAnalysis(String originalContent, String summary, String priority, List<String> suggestedSolutions) {
        this.originalContent = originalContent;
        this.summary = summary;
        this.priority = priority;
        this.suggestedSolutions = suggestedSolutions;
    }

    // Getters and setters
    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getSuggestedSolutions() {
        return suggestedSolutions;
    }

    public void setSuggestedSolutions(List<String> suggestedSolutions) {
        this.suggestedSolutions = suggestedSolutions;
    }
}
