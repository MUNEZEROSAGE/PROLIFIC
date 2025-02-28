package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ContaminationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // "INCORRECT_SORTING", "HAZARDOUS_WASTE"
    private String description;
    private String status; // "OPEN", "RESOLVED"
    private LocalDateTime reportedAt;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
}
