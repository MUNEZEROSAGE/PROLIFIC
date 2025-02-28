package com.finalProject.repository;

import com.finalProject.model.ContaminationReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaminationReportRepository extends JpaRepository<ContaminationReport, Long> {
    // Filter reports by status (OPEN/RESOLVED) or type (INCORRECT_SORTING/HAZARDOUS_WASTE)
    Page<ContaminationReport> findByStatus(String status, Pageable pageable);
    Page<ContaminationReport> findByType(String type, Pageable pageable);
}