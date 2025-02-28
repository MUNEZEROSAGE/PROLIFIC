package com.finalProject.serviceImpl;

import com.finalProject.model.ContaminationReport;
import com.finalProject.repository.ContaminationReportRepository;
import com.finalProject.service.ContaminationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContaminationReportServiceImpl implements ContaminationReportService {

    private final ContaminationReportRepository contaminationReportRepository;

    @Autowired
    public ContaminationReportServiceImpl(ContaminationReportRepository contaminationReportRepository) {
        this.contaminationReportRepository = contaminationReportRepository;
    }

    @Override
    public ContaminationReport saveContaminationReport(ContaminationReport report) {
        return contaminationReportRepository.save(report);
    }

    @Override
    public Page<ContaminationReport> getAllContaminationReports(Pageable pageable) {
        return contaminationReportRepository.findAll(pageable);
    }

    @Override
    public ContaminationReport getContaminationReportById(Long id) {
        return contaminationReportRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteContaminationReport(Long id) {
        contaminationReportRepository.deleteById(id);
    }

    @Override
    public ContaminationReport updateContaminationReport(Long id, ContaminationReport report) {
        if (contaminationReportRepository.existsById(id)) {
            report.setId(id);
            return contaminationReportRepository.save(report);
        }
        throw new RuntimeException("ContaminationReport not found with id: " + id);
    }

    @Override
    public Page<ContaminationReport> findByStatus(String status, Pageable pageable) {
        return contaminationReportRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<ContaminationReport> findByType(String type, Pageable pageable) {
        return contaminationReportRepository.findByType(type, pageable);
    }
}