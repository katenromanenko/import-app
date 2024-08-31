package com.example.importapp.controller;

import com.example.importapp.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController {
    private final ImportService importService;

    @Autowired
    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importData() {
        try {
            importService.importData();
            return ResponseEntity.ok("Data import started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred during data import: " + e.getMessage());
        }
    }
}