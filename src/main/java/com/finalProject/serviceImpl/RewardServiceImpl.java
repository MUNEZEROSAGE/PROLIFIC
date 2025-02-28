package com.finalProject.serviceImpl;

import com.finalProject.model.Reward;
import com.finalProject.repository.RewardRepository;
import com.finalProject.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;

    @Autowired
    public RewardServiceImpl(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @Override
    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    @Override
    public Page<Reward> getAllRewards(Pageable pageable) {
        return rewardRepository.findAll(pageable);
    }

    @Override
    public Reward getRewardById(Long id) {
        return rewardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }

    @Override
    public Reward updateReward(Long id, Reward reward) {
        if (rewardRepository.existsById(id)) {
            reward.setId(id);
            return rewardRepository.save(reward);
        }
        throw new RuntimeException("Reward not found with id: " + id);
    }

    @Override
    public Page<Reward> findByIsActive(boolean isActive, Pageable pageable) {
        return rewardRepository.findByIsActive(isActive, pageable);
    }
}