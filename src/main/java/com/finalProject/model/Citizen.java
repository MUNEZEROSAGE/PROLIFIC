package com.finalProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private LocalDate createdAt;
    private String Location;
    private int tokens;

    @OneToMany(mappedBy = "citizen")
    private List<WasteItem> scannedItems;
    @OneToOne(mappedBy = "citizen", cascade = CascadeType.ALL, orphanRemoval = true)
    private EcoPoints ecoPoints;


    private String password;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profilePic;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "citizen_roles", joinColumns = @JoinColumn(name = "citizen_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();
    private boolean enabled = true;

    public void addRole(String role) {
        this.roles.add(role);
    }

    public void removeRole(String role) {
        this.roles.remove(role);
    }

    // Method to add tokens
    public void addEcoPoints(int tokens) {
        if (this.ecoPoints == null) {
            this.ecoPoints = new EcoPoints();
        }
        this.ecoPoints.addTokens(tokens);
    }
}