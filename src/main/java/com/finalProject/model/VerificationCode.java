package com.finalProject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;          // e.g., "A1B2C3"
    private Long binId;           // Associated bin
    private int tokens;           // Tokens awarded
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt; // Code validity period (e.g., 10 minutes)
    private boolean isUsed;       // Prevent reuse
}