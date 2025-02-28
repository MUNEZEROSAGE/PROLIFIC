package com.finalProject.repository;

import com.finalProject.model.CollectionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRequestRepository extends JpaRepository<CollectionRequest, Long> {
    // Find requests by status (PENDING/COMPLETED)
    Page<CollectionRequest> findByStatus(String status, Pageable pageable);

    // Find requests for a specific SmartBin
    @Query("SELECT cr FROM CollectionRequest cr WHERE cr.smartBin.id = :smartBinId")
    Page<CollectionRequest> findBySmartBinId(@Param("smartBinId") Long smartBinId, Pageable pageable);
}