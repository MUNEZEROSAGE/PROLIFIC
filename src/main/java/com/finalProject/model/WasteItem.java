package com.finalProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagePath;
    private String classification;
    private LocalDateTime scannedAt;
    //Relationships
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

}
