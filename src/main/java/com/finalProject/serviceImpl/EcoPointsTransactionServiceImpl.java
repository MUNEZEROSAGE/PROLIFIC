package com.finalProject.serviceImpl;

import com.finalProject.model.EcoPointsTransaction;
import com.finalProject.repository.EcoPointsTransactionRepository;
import com.finalProject.service.EcoPointsTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EcoPointsTransactionServiceImpl implements EcoPointsTransactionService {

    private final EcoPointsTransactionRepository ecoPointsTransactionRepository;

    @Autowired
    public EcoPointsTransactionServiceImpl(EcoPointsTransactionRepository ecoPointsTransactionRepository) {
        this.ecoPointsTransactionRepository = ecoPointsTransactionRepository;
    }

    @Override
    public EcoPointsTransaction saveEcoPointsTransaction(EcoPointsTransaction transaction) {
        return ecoPointsTransactionRepository.save(transaction);
    }

    @Override
    public Page<EcoPointsTransaction> getAllEcoPointsTransactions(Pageable pageable) {
        return ecoPointsTransactionRepository.findAll(pageable);
    }

    @Override
    public EcoPointsTransaction getEcoPointsTransactionById(Long id) {
        return ecoPointsTransactionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteEcoPointsTransaction(Long id) {
        ecoPointsTransactionRepository.deleteById(id);
    }

    @Override
    public EcoPointsTransaction updateEcoPointsTransaction(Long id, EcoPointsTransaction transaction) {
        if (ecoPointsTransactionRepository.existsById(id)) {
            transaction.setId(id);
            return ecoPointsTransactionRepository.save(transaction);
        }
        throw new RuntimeException("EcoPointsTransaction not found with id: " + id);
    }

    @Override
    public Page<EcoPointsTransaction> findByType(String type, Pageable pageable) {
        return ecoPointsTransactionRepository.findByType(type, pageable);
    }
}