package com.finalProject.service;

import com.finalProject.model.WasteItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WasteItemService {
    WasteItem saveWasteItem(WasteItem wasteItem);
    Page<WasteItem> getAllWasteItems(Pageable pageable);
    WasteItem getWasteItemById(Long id);
    void deleteWasteItem(Long id);
    WasteItem updateWasteItem(Long id, WasteItem wasteItem);
    Page<WasteItem> findByClassification(String classification, Pageable pageable);
    Page<WasteItem> findByCitizenId(Long citizenId, Pageable pageable);
}