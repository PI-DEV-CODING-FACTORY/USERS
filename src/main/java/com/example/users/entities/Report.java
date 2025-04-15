package com.example.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Report {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @JsonIgnore
    Long id;
    @JsonIgnore
    String userId;
    @JsonIgnore
    String userRole;
    String title;
    String description;
    @JsonIgnore
    ReportStatus status;
     @JsonIgnore
     LocalDateTime dateTime;
    @JsonIgnore
     private boolean aiAnalyzed = false;
    @Column(length = 10000)
     String reportAnalysis;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userRole='" + userRole + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", aiAnalyzed=" + aiAnalyzed +
                ", reportAnalysis='" + reportAnalysis + '\'' +
                '}';
    }
}
