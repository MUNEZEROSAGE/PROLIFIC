package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EcoPointsTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // "EARNED", "REDEEMED"
    private int amount;
    private LocalDateTime timestamp;
    // Relationships
    @ManyToOne
    @JoinColumn(name = "eco_points_id")
    private EcoPoints ecoPoints;
}
