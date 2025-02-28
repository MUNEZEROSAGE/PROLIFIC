package com.finalProject.repository;

import com.finalProject.model.WasteItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteItemRepository extends JpaRepository<WasteItem, Long> {
    // Filter items by classification (PLASTIC/GLASS/ORGANIC)
    Page<WasteItem> findByClassification(String classification, Pageable pageable);

    // Find items scanned by a specific citizen
    Page<WasteItem> findByCitizenId(Long citizenId, Pageable pageable);
}