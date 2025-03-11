package com.example.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Report {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @JsonIgnore
    Long id;
    String userId;
    String title;
    String description;
    @JsonIgnore
    ReportStatus status;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
