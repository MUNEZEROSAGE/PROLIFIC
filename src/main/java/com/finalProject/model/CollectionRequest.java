package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CollectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status; // "PENDING", "COMPLETED"
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "smart_bin_id")
    private SmartBin smartBin;
}
