package com.example.importapp.repository;

import com.example.importapp.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    PatientProfile findByOldClientGuid(String oldClientGuid);
}
