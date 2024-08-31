package com.example.importapp.repository;

import com.example.importapp.model.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
    PatientNote findByNoteGuid(String noteGuid);
}