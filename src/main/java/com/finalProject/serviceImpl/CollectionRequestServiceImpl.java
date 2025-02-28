package com.finalProject.serviceImpl;

import com.finalProject.model.CollectionRequest;
import com.finalProject.repository.CollectionRequestRepository;
import com.finalProject.service.CollectionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CollectionRequestServiceImpl implements CollectionRequestService {

    private final CollectionRequestRepository collectionRequestRepository;

    @Autowired
    public CollectionRequestServiceImpl(CollectionRequestRepository collectionRequestRepository) {
        this.collectionRequestRepository = collectionRequestRepository;
    }

    @Override
    public CollectionRequest saveCollectionRequest(CollectionRequest request) {
        return collectionRequestRepository.save(request);
    }

    @Override
    public Page<CollectionRequest> getAllCollectionRequests(Pageable pageable) {
        return collectionRequestRepository.findAll(pageable);
    }

    @Override
    public CollectionRequest getCollectionRequestById(Long id) {
        return collectionRequestRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCollectionRequest(Long id) {
        collectionRequestRepository.deleteById(id);
    }

    @Override
    public CollectionRequest updateCollectionRequest(Long id, CollectionRequest request) {
        if (collectionRequestRepository.existsById(id)) {
            request.setId(id);
            return collectionRequestRepository.save(request);
        }
        throw new RuntimeException("CollectionRequest not found with id: " + id);
    }

    @Override
    public Page<CollectionRequest> findByStatus(String status, Pageable pageable) {
        return collectionRequestRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<CollectionRequest> findBySmartBinId(Long smartBinId, Pageable pageable) {
        return collectionRequestRepository.findBySmartBinId(smartBinId, pageable);
    }
}