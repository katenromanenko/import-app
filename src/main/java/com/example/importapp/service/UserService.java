package com.example.importapp.service;

import com.example.importapp.model.CompanyUser;
import com.example.importapp.repository.CompanyUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CompanyUserRepository companyUserRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(CompanyUserRepository companyUserRepository) {
        this.companyUserRepository = companyUserRepository;
    }
    @Transactional
    public CompanyUser findOrCreateUser(String login) {
        CompanyUser user = companyUserRepository.findByLogin(login);
        if (user == null) {
            user = new CompanyUser();
            user.setLogin(login);
            companyUserRepository.save(user);
            logger.info("Created new user with login: {}", login);
        } else {
            logger.info("User with login {} already exists", login);
        }
        return user;
    }
}
