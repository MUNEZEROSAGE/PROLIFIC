package com.finalProject.service;

import com.finalProject.model.EcoPoints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EcoPointsService {
    EcoPoints saveEcoPoints(EcoPoints ecoPoints);
    EcoPoints getEcoPointsById(Long id);
    EcoPoints getEcoPointsByCitizenId(Long citizenId);
    void deleteEcoPoints(Long id);
    EcoPoints updateEcoPoints(Long id, EcoPoints ecoPoints);
}