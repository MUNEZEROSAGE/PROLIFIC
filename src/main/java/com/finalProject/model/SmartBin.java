package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SmartBin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location; // GPS coordinates
    private double fillLevel; // 0-100%
    private String status; // "EMPTY", "HALF_FULL", "FULL"
    private LocalDateTime lastCollectedAt;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] qrCode;

    @OneToMany(mappedBy = "smartBin")
    private List<CollectionRequest> requests;
}
