package com.finalProject.serviceImpl;

import com.finalProject.model.CollectionRequest;
import com.finalProject.model.SmartBin;
import com.finalProject.model.WasteItem;
import com.finalProject.repository.WasteItemRepository;
import com.finalProject.service.CollectionRequestService;
import com.finalProject.service.SmartBinService;
import com.finalProject.service.WasteItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WasteItemServiceImpl implements WasteItemService {

    private final WasteItemRepository wasteItemRepository;
    private final SmartBinService smartBinService; // Add this
    private final CollectionRequestService collectionRequestService; // Add this

    @Autowired
    public WasteItemServiceImpl(WasteItemRepository wasteItemRepository,
                                SmartBinService smartBinService, // Add this
                                CollectionRequestService collectionRequestService) { // Add this
        this.wasteItemRepository = wasteItemRepository;
        this.smartBinService = smartBinService; // Initialize
        this.collectionRequestService = collectionRequestService; // Initialize
    }

    @Override
    public WasteItem saveWasteItem(WasteItem wasteItem) {
        // Save waste item
        WasteItem savedItem = wasteItemRepository.save(wasteItem);
        // Update bin's fill level
        SmartBin bin = savedItem.getBin(); // Ensure getBin() exists
        if (bin != null) {
            bin.setFillLevel(bin.getFillLevel() + 5); // Example: Increase by 5% per item
            smartBinService.updateSmartBin(bin.getId(), bin);
            // Check if fill level >85%
            if (bin.getFillLevel() > 85) {
                CollectionRequest request = new CollectionRequest();
                request.setSmartBin(bin);
                request.setStatus("PENDING");
                request.setRequestedAt(LocalDateTime.now());
                collectionRequestService.saveCollectionRequest(request);
            }
        }

        return savedItem;
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