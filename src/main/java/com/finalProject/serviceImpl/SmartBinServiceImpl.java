package com.finalProject.serviceImpl;

import com.finalProject.model.SmartBin;
import com.finalProject.repository.SmartBinRepository;
import com.finalProject.service.SmartBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SmartBinServiceImpl implements SmartBinService {

    private final SmartBinRepository smartBinRepository;

    @Autowired
    public SmartBinServiceImpl(SmartBinRepository smartBinRepository) {
        this.smartBinRepository = smartBinRepository;
    }

    @Override
    public SmartBin saveSmartBin(SmartBin smartBin) {
        return smartBinRepository.save(smartBin);
    }

    @Override
    public Page<SmartBin> getAllSmartBins(Pageable pageable) {
        return smartBinRepository.findAll(pageable);
    }

    @Override
    public SmartBin getSmartBinById(Long id) {
        return smartBinRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSmartBin(Long id) {
        smartBinRepository.deleteById(id);
    }

    @Override
    public SmartBin updateSmartBin(Long id, SmartBin smartBin) {
        if (smartBinRepository.existsById(id)) {
            smartBin.setId(id);
            return smartBinRepository.save(smartBin);
        }
        throw new RuntimeException("SmartBin not found with id: " + id);
    }

    @Override
    public Page<SmartBin> findByStatus(String status, Pageable pageable) {
        return smartBinRepository.findByStatus(status, pageable);
    }
}