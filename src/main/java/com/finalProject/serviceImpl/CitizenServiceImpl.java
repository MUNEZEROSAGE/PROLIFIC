package com.finalProject.serviceImpl;

import com.finalProject.model.*;
import com.finalProject.repository.CitizenRepository;
import com.finalProject.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitizenServiceImpl implements CitizenService {
    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    @Transactional
    public Citizen saveCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    @Override
    public Page<Citizen> getAllCitizens(Pageable pageable) {
        return citizenRepository.findAll(pageable);
    }

    @Override
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll(); // Non-paginated
    }

    @Override
    public Citizen getCitizenById(Long id) {
        return citizenRepository.findById(id).orElse(null);
    }

    @Override
    public Citizen getCitizenByEmail(String email) {
        return citizenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found with email: " + email));
    }

    @Override
    @Transactional
    public void deleteCitizen(Long id) {
        citizenRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Citizen updateCitizen(Long id, Citizen citizen) {
        if (citizenRepository.existsById(id)) {
            citizen.setId(id);
            return citizenRepository.save(citizen);
        }
        throw new RuntimeException("Citizen not found with id: " + id);
    }

    @Override
    public boolean existsByCitizenId(Long id) {
        return citizenRepository.existsById(id);
    }

    @Override
    public Citizen getCitizenByName(String name) {
        return citizenRepository.findByName(name);
    }

    @Override
    public boolean existsByCitizenName(String name) {
        return citizenRepository.existsByName(name);
    }

    @Override
    public List<Citizen> findCitizensByRole(String role) {
        return citizenRepository.findByRole(role);
    }

    @Override
    public Page<Citizen> findCitizensByRolePaged(String role, Pageable pageable) {
        return citizenRepository.findByRolePaged(role, pageable);
    }

    @Override
    @Transactional
    public void addRoleToCitizen(Long citizenId, String role) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found with id: " + citizenId));
        citizen.addRole(role);
        citizenRepository.save(citizen);
    }

    @Override
    @Transactional
    public void removeRoleFromCitizen(Long citizenId, String role) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found with id: " + citizenId));
        citizen.removeRole(role);
        citizenRepository.save(citizen);
    }

    @Override
    public long countCitizensByRole(String role) {
        return citizenRepository.findByRole(role).size();
    }

    @Override
    public long getTotalCitizensCount() {
        return citizenRepository.count();
    }

    @Override
    @Transactional
    public void addEcoPoints(Long citizenId, int points) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found with id: " + citizenId));

        // Get or create EcoPoints for the citizen
        EcoPoints ecoPoints = citizen.getEcoPoints();
        if (ecoPoints == null) {
            ecoPoints = new EcoPoints();
            ecoPoints.setCitizen(citizen); // Set the citizen
            citizen.setEcoPoints(ecoPoints); // Set the EcoPoints in the citizen
        }

        // Add points
        ecoPoints.setBalance(ecoPoints.getBalance() + points);

        // Save the citizen (cascade will save EcoPoints)
        citizenRepository.save(citizen);
    }


}
