package com.finalProject.service;

import com.finalProject.model.SmartBin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SmartBinService {
    SmartBin saveSmartBin(SmartBin smartBin);
    Page<SmartBin> getAllSmartBins(Pageable pageable);
    SmartBin getSmartBinById(Long id);
    void deleteSmartBin(Long id);
    SmartBin updateSmartBin(Long id, SmartBin smartBin);
    Page<SmartBin> findByStatus(String status, Pageable pageable);
}