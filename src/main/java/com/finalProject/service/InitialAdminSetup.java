package com.finalProject.service;


import com.finalProject.model.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialAdminSetup implements CommandLineRunner {

    private final CitizenService citizenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialAdminSetup(CitizenService citizenService, PasswordEncoder passwordEncoder) {
        this.citizenService = citizenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("InitialAdminSetup is running...");

        // Check if there are any citizens in the database
        Page<Citizen> citizenPage = citizenService.getAllCitizens(PageRequest.of(0, 1));

        if (citizenPage.getTotalElements() == 0) {
            Citizen adminCitizen = new Citizen();
            adminCitizen.setName("Admin");
            adminCitizen.setEmail("admin@example.com");
            adminCitizen.setPassword(passwordEncoder.encode("adminPassword"));
            adminCitizen.setPhoneNumber("1234567890");
            adminCitizen.addRole("ADMIN");

            citizenService.saveCitizen(adminCitizen);

            System.out.println("Initial admin user created.");
            System.out.println("Email: " + adminCitizen.getEmail());
            System.out.println("Password: adminPassword"); // Don't log the encoded password
        } else {
            System.out.println("Admin user already exists. Skipping creation.");
        }
    }
}