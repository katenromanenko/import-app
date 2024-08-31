package com.example.importapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "patient_profile", uniqueConstraints = @UniqueConstraint(columnNames = "old_client_guid"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "old_client_guid", nullable = false, unique = true)
    private String oldClientGuid;

    @Column(name = "status_id", nullable = false)
    private Short statusId;
}