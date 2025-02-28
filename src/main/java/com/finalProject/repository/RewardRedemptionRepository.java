package com.finalProject.repository;

import com.finalProject.model.RewardRedemption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Long> {
    // Find redemptions by citizen or reward
    Page<RewardRedemption> findByCitizenId(Long citizenId, Pageable pageable);
    Page<RewardRedemption> findByRewardId(Long rewardId, Pageable pageable);
}