package com.finalProject.service;

import com.finalProject.model.EcoPointsTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EcoPointsTransactionService {
    EcoPointsTransaction saveEcoPointsTransaction(EcoPointsTransaction transaction);
    Page<EcoPointsTransaction> getAllEcoPointsTransactions(Pageable pageable);
    EcoPointsTransaction getEcoPointsTransactionById(Long id);
    void deleteEcoPointsTransaction(Long id);
    EcoPointsTransaction updateEcoPointsTransaction(Long id, EcoPointsTransaction transaction);
    Page<EcoPointsTransaction> findByType(String type, Pageable pageable);
}