package com.example.users.repositories;

import com.example.users.entities.Report;
import com.example.users.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.userId = ?1")
    ReportStatus findByUserId(String id);
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByAiAnalyzed(boolean aiAnalyzed);
}
