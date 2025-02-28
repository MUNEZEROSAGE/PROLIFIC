package com.finalProject.service;

import com.finalProject.model.ContaminationReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContaminationReportService {
    ContaminationReport saveContaminationReport(ContaminationReport report);
    Page<ContaminationReport> getAllContaminationReports(Pageable pageable);
    ContaminationReport getContaminationReportById(Long id);
    void deleteContaminationReport(Long id);
    ContaminationReport updateContaminationReport(Long id, ContaminationReport report);
    Page<ContaminationReport> findByStatus(String status, Pageable pageable);
    Page<ContaminationReport> findByType(String type, Pageable pageable);
}