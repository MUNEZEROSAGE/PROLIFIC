package com.finalProject.serviceImpl;

import com.finalProject.model.WasteItem;
import com.finalProject.repository.WasteItemRepository;
import com.finalProject.service.WasteItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WasteItemServiceImpl implements WasteItemService {

    private final WasteItemRepository wasteItemRepository;

    @Autowired
    public WasteItemServiceImpl(WasteItemRepository wasteItemRepository) {
        this.wasteItemRepository = wasteItemRepository;
    }

    @Override
    public WasteItem saveWasteItem(WasteItem wasteItem) {
        return wasteItemRepository.save(wasteItem);
    }

    @Override
    public Page<WasteItem> getAllWasteItems(Pageable pageable) {
        return wasteItemRepository.findAll(pageable);
    }

    @Override
    public WasteItem getWasteItemById(Long id) {
        return wasteItemRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteWasteItem(Long id) {
        wasteItemRepository.deleteById(id);
    }

    @Override
    public WasteItem updateWasteItem(Long id, WasteItem wasteItem) {
        if (wasteItemRepository.existsById(id)) {
            wasteItem.setId(id);
            return wasteItemRepository.save(wasteItem);
        }
        throw new RuntimeException("WasteItem not found with id: " + id);
    }

    @Override
    public Page<WasteItem> findByClassification(String classification, Pageable pageable) {
        return wasteItemRepository.findByClassification(classification, pageable);
    }

    @Override
    public Page<WasteItem> findByCitizenId(Long citizenId, Pageable pageable) {
        return wasteItemRepository.findByCitizenId(citizenId, pageable);
    }
}