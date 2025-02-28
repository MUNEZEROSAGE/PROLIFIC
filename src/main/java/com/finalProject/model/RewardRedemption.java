package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RewardRedemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime redeemedAt;
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
    @ManyToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;
}
