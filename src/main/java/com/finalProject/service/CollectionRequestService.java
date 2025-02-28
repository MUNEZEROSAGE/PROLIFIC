package com.finalProject.service;

import com.finalProject.model.CollectionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionRequestService {
    CollectionRequest saveCollectionRequest(CollectionRequest request);
    Page<CollectionRequest> getAllCollectionRequests(Pageable pageable);
    CollectionRequest getCollectionRequestById(Long id);
    void deleteCollectionRequest(Long id);
    CollectionRequest updateCollectionRequest(Long id, CollectionRequest request);
    Page<CollectionRequest> findByStatus(String status, Pageable pageable);
    Page<CollectionRequest> findBySmartBinId(Long smartBinId, Pageable pageable);
}