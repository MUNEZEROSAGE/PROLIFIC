package com.finalProject.repository;


import com.finalProject.model.Citizen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Page<Citizen> findAll(Pageable pageable);
    Citizen findByName(String name);
    boolean existsByName(String name);
    Optional<Citizen> findByEmail(String email);
    @Query("SELECT w FROM Citizen w JOIN w.roles r WHERE r = :role")
    List<Citizen> findByRole(@Param("role") String role);
    @Query("SELECT w FROM Citizen w JOIN w.roles r WHERE r = :role")
    Page<Citizen> findByRolePaged(@Param("role") String role, Pageable pageable);
}

