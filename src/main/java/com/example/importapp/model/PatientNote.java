package com.example.importapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_note", uniqueConstraints = @UniqueConstraint(columnNames = "note_guid"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time", nullable = false)
    private LocalDateTime lastModifiedDateTime;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private CompanyUser createdByUser;

    @ManyToOne
    @JoinColumn(name = "last_modified_by_user_id")
    private CompanyUser lastModifiedByUser;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @Column(name = "note_guid", nullable = false)
    private String noteGuid;

}