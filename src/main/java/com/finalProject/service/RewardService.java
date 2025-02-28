package com.finalProject.service;

import com.finalProject.model.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardService {
    Reward saveReward(Reward reward);
    Page<Reward> getAllRewards(Pageable pageable);
    Reward getRewardById(Long id);
    void deleteReward(Long id);
    Reward updateReward(Long id, Reward reward);
    Page<Reward> findByIsActive(boolean isActive, Pageable pageable);
}