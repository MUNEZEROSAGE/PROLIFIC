package com.finalProject.service;



import com.finalProject.model.Citizen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CitizenService {
    Citizen saveCitizen(Citizen citizen);
    Page<Citizen> getAllCitizens(Pageable pageable);
    Citizen getCitizenById(Long id);
    Citizen getCitizenByEmail(String email);
    void deleteCitizen(Long id);
    Citizen updateCitizen(Long id, Citizen citizen);
    boolean existsByCitizenId(Long id);
    Citizen getCitizenByName(String name);
    boolean existsByCitizenName(String name);
    List<Citizen> findCitizensByRole(String role);
    Page<Citizen> findCitizensByRolePaged(String role, Pageable pageable);
    void addRoleToCitizen(Long citizenId, String role);
    void removeRoleFromCitizen(Long citizenId, String role);
    long countCitizensByRole(String role);
    long getTotalCitizensCount();

}
