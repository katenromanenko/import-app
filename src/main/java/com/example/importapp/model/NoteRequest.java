package com.example.importapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteRequest {
    private String agency;
    private String clientGuid;
}
