package com.example.importapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Note {
    private String comments;
    private String guid;
    private LocalDateTime modifiedDateTime;
    private String clientGuid;
    private LocalDateTime datetime; // No changes to this field as requested
    private String loggedUser;
    private LocalDateTime createdDateTime;

    @Setter
    @Getter
    private String createdDateTimeString;

    @Setter
    @Getter
    private String modifiedDateTimeString;

    // Convert LocalDateTime to String
    public String convertToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return dateTime.format(formatter);
    }

    // Convert String to LocalDateTime
    public LocalDateTime convertToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
