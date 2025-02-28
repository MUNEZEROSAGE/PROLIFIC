package com.finalProject.repository;

import com.finalProject.model.SmartBin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartBinRepository extends JpaRepository<SmartBin, Long> {
    // Filter bins by status (EMPTY/HALF_FULL/FULL)
    Page<SmartBin> findByStatus(String status, Pageable pageable);
}