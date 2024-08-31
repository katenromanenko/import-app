package com.example.importapp.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Client {

    @NotNull
    private String agency;

    @NotNull
    private String guid;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String status;
    private LocalDate dob;
    private LocalDateTime createdDateTime = LocalDateTime.now();
}