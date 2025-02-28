package com.finalProject.serviceImpl;

import com.finalProject.model.EcoPoints;
import com.finalProject.repository.EcoPointsRepository;
import com.finalProject.service.EcoPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcoPointsServiceImpl implements EcoPointsService {

    private final EcoPointsRepository ecoPointsRepository;

    @Autowired
    public EcoPointsServiceImpl(EcoPointsRepository ecoPointsRepository) {
        this.ecoPointsRepository = ecoPointsRepository;
    }

    @Override
    public EcoPoints saveEcoPoints(EcoPoints ecoPoints) {
        return ecoPointsRepository.save(ecoPoints);
    }

    @Override
    public EcoPoints getEcoPointsById(Long id) {
        return ecoPointsRepository.findById(id).orElse(null);
    }

    @Override
    public EcoPoints getEcoPointsByCitizenId(Long citizenId) {
        return ecoPointsRepository.findByCitizenId(citizenId);
    }

    @Override
    public void deleteEcoPoints(Long id) {
        ecoPointsRepository.deleteById(id);
    }

    @Override
    public EcoPoints updateEcoPoints(Long id, EcoPoints ecoPoints) {
        if (ecoPointsRepository.existsById(id)) {
            ecoPoints.setId(id);
            return ecoPointsRepository.save(ecoPoints);
        }
        throw new RuntimeException("EcoPoints not found with id: " + id);
    }
}