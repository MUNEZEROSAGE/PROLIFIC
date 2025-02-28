package com.finalProject.service;

import com.finalProject.model.RewardRedemption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardRedemptionService {
    RewardRedemption saveRewardRedemption(RewardRedemption redemption);
    Page<RewardRedemption> getAllRewardRedemptions(Pageable pageable);
    RewardRedemption getRewardRedemptionById(Long id);
    void deleteRewardRedemption(Long id);
    RewardRedemption updateRewardRedemption(Long id, RewardRedemption redemption);
    Page<RewardRedemption> findByCitizenId(Long citizenId, Pageable pageable);
    Page<RewardRedemption> findByRewardId(Long rewardId, Pageable pageable);
}