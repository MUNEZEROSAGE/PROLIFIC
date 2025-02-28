package com.finalProject.repository;

import com.finalProject.model.EcoPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcoPointsRepository extends JpaRepository<EcoPoints, Long> {
    // Find EcoPoints by Citizen (1:1 relationship)
    EcoPoints findByCitizenId(Long citizenId);
}