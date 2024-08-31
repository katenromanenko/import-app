package com.example.importapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_user", indexes = @Index(columnList = "login"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String login;
}

