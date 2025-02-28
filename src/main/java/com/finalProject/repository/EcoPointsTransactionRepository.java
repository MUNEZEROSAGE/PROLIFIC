package com.finalProject.repository;

import com.finalProject.model.EcoPointsTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcoPointsTransactionRepository extends JpaRepository<EcoPointsTransaction, Long> {
    // Filter transactions by type (EARNED/REDEEMED)
    Page<EcoPointsTransaction> findByType(String type, Pageable pageable);
}