package com.example.importapp.repository;

import com.example.importapp.model.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    CompanyUser findByLogin(String login);
}