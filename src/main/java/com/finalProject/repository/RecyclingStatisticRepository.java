package com.finalProject.repository;

import com.finalProject.model.RecyclingStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RecyclingStatisticRepository extends JpaRepository<RecyclingStatistic, Long> {
    // Filter stats by region or date
    Page<RecyclingStatistic> findByRegion(String region, Pageable pageable);
    Page<RecyclingStatistic> findByDate(LocalDate date, Pageable pageable);
}