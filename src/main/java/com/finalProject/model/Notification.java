package com.finalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private boolean isRead;
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
}
