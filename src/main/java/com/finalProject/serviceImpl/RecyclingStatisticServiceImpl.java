package com.finalProject.serviceImpl;

import com.finalProject.model.RecyclingStatistic;
import com.finalProject.repository.RecyclingStatisticRepository;
import com.finalProject.service.RecyclingStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecyclingStatisticServiceImpl implements RecyclingStatisticService {

    private final RecyclingStatisticRepository recyclingStatisticRepository;

    @Autowired
    public RecyclingStatisticServiceImpl(RecyclingStatisticRepository recyclingStatisticRepository) {
        this.recyclingStatisticRepository = recyclingStatisticRepository;
    }

    @Override
    public RecyclingStatistic saveRecyclingStatistic(RecyclingStatistic statistic) {
        return recyclingStatisticRepository.save(statistic);
    }

    @Override
    public Page<RecyclingStatistic> getAllRecyclingStatistics(Pageable pageable) {
        return recyclingStatisticRepository.findAll(pageable);
    }

    @Override
    public RecyclingStatistic getRecyclingStatisticById(Long id) {
        return recyclingStatisticRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRecyclingStatistic(Long id) {
        recyclingStatisticRepository.deleteById(id);
    }

    @Override
    public RecyclingStatistic updateRecyclingStatistic(Long id, RecyclingStatistic statistic) {
        if (recyclingStatisticRepository.existsById(id)) {
            statistic.setId(id);
            return recyclingStatisticRepository.save(statistic);
        }
        throw new RuntimeException("RecyclingStatistic not found with id: " + id);
    }

    @Override
    public Page<RecyclingStatistic> findByRegion(String region, Pageable pageable) {
        return recyclingStatisticRepository.findByRegion(region, pageable);
    }

    @Override
    public Page<RecyclingStatistic> findByDate(LocalDate date, Pageable pageable) {
        return recyclingStatisticRepository.findByDate(date, pageable);
    }
}