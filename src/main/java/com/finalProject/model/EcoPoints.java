package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EcoPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int balance;
    // Relationships
    @OneToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
    @OneToMany(mappedBy = "ecoPoints")
    private List<EcoPointsTransaction> transactions;
}
