package com.example.users.repositories;

import com.example.users.entities.Report;
import com.example.users.entities.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface ReportRepo extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.userId = ?1")
    ReportStatus findByUserId(String id);
    List<Report> findByStatus(ReportStatus status);
}
