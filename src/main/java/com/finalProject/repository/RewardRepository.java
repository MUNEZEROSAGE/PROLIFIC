package com.finalProject.repository;

import com.finalProject.model.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    // Filter rewards by active status
    Page<Reward> findByIsActive(boolean isActive, Pageable pageable);
}