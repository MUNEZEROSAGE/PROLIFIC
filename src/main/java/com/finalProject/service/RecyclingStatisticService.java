package com.finalProject.service;

import com.finalProject.model.RecyclingStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RecyclingStatisticService {
    RecyclingStatistic saveRecyclingStatistic(RecyclingStatistic statistic);
    Page<RecyclingStatistic> getAllRecyclingStatistics(Pageable pageable);
    RecyclingStatistic getRecyclingStatisticById(Long id);
    void deleteRecyclingStatistic(Long id);
    RecyclingStatistic updateRecyclingStatistic(Long id, RecyclingStatistic statistic);
    Page<RecyclingStatistic> findByRegion(String region, Pageable pageable);
    Page<RecyclingStatistic> findByDate(LocalDate date, Pageable pageable);
}