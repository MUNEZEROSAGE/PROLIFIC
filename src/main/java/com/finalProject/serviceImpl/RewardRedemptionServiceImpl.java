package com.finalProject.serviceImpl;

import com.finalProject.model.RewardRedemption;
import com.finalProject.repository.RewardRedemptionRepository;
import com.finalProject.service.RewardRedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RewardRedemptionServiceImpl implements RewardRedemptionService {

    private final RewardRedemptionRepository rewardRedemptionRepository;

    @Autowired
    public RewardRedemptionServiceImpl(RewardRedemptionRepository rewardRedemptionRepository) {
        this.rewardRedemptionRepository = rewardRedemptionRepository;
    }

    @Override
    public RewardRedemption saveRewardRedemption(RewardRedemption redemption) {
        return rewardRedemptionRepository.save(redemption);
    }

    @Override
    public Page<RewardRedemption> getAllRewardRedemptions(Pageable pageable) {
        return rewardRedemptionRepository.findAll(pageable);
    }

    @Override
    public RewardRedemption getRewardRedemptionById(Long id) {
        return rewardRedemptionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRewardRedemption(Long id) {
        rewardRedemptionRepository.deleteById(id);
    }

    @Override
    public RewardRedemption updateRewardRedemption(Long id, RewardRedemption redemption) {
        if (rewardRedemptionRepository.existsById(id)) {
            redemption.setId(id);
            return rewardRedemptionRepository.save(redemption);
        }
        throw new RuntimeException("RewardRedemption not found with id: " + id);
    }

    @Override
    public Page<RewardRedemption> findByCitizenId(Long citizenId, Pageable pageable) {
        return rewardRedemptionRepository.findByCitizenId(citizenId, pageable);
    }

    @Override
    public Page<RewardRedemption> findByRewardId(Long rewardId, Pageable pageable) {
        return rewardRedemptionRepository.findByRewardId(rewardId, pageable);
    }
}