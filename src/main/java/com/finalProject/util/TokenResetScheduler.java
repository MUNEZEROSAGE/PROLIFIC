package com.finalProject.util;


import com.finalProject.model.Citizen;
import com.finalProject.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenResetScheduler {

    @Autowired
    private CitizenService citizenService;

    @Scheduled(cron = "0 0 0 1 * *") // Runs at midnight on the 1st day of every month
    public void resetTokensAndIssueRewards() {
        List<Citizen> citizens = citizenService.getAllCitizens();

        citizens.forEach(citizen -> {
            // Issue rewards based on tokens
            if (citizen.getTokens() > 0) {
                issueReward(citizen.getId(), citizen.getTokens());
            }
            // Reset tokens
            citizen.setTokens(0);
            citizenService.saveCitizen(citizen);
        });
    }

    private void issueReward(Long citizenId, int tokens) {
        // Implement reward logic (e.g., assign eco-points)
    }
}
