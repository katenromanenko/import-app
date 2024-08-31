package com.example.importapp.service;

import com.example.importapp.model.Client;
import com.example.importapp.model.CompanyUser;
import com.example.importapp.model.Note;
import com.example.importapp.model.PatientNote;
import com.example.importapp.model.PatientProfile;
import com.example.importapp.repository.PatientNoteRepository;
import com.example.importapp.repository.PatientProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ImportService {

    private final ClientService clientService;
    private final NoteService noteService;
    private final UserService userService;
    private final PatientProfileRepository patientProfileRepository;
    private final PatientNoteRepository patientNoteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    public ImportService(ClientService clientService, NoteService noteService, UserService userService,
                         PatientProfileRepository patientProfileRepository, PatientNoteRepository patientNoteRepository) {
        this.clientService = clientService;
        this.noteService = noteService;
        this.userService = userService;
        this.patientProfileRepository = patientProfileRepository;
        this.patientNoteRepository = patientNoteRepository;
    }

    @Scheduled(cron = "0 15 0/2 * * ?") // Executes every two hours at 15 minutes past the hour
    public void importNotes() {
        try {
            List<Client> clients = clientService.fetchClients();
            if (clients.isEmpty()) {
                logger.warn("No clients were retrieved from the old system.");
                return;
            }

            for (Client client : clients) {
                List<Note> notes = noteService.fetchNotes(client);
                if (notes == null || notes.isEmpty()) {
                    logger.warn("No notes were retrieved for client with GUID: {}", client.getGuid());
                    continue;
                }

                for (Note note : notes) {
                    processNote(note, client);
                }
            }
        } catch (HttpClientErrorException e) {
            logger.error("Error retrieving data from the old system: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error during import: {}", e.getMessage(), e);
        }
    }

    public void importData() {
        importNotes();
    }

    private void processNote(Note note, Client client) {
        if (note == null || client == null) {
            logger.warn("Note or client not found.");
            return;
        }

        CompanyUser user = userService.findOrCreateUser(note.getLoggedUser());
        PatientProfile patient = findPatientByOldClientGuid(client.getGuid());
        if (patient == null || !List.of((short)200, (short)210, (short)230).contains(patient.getStatusId())) {
            logger.warn("Patient with oldClientGuid {} not found or inactive.", client.getGuid());
            return;
        }

        PatientNote patientNote = findOrCreatePatientNote(note, user, patient);
        patientNoteRepository.save(patientNote);
    }

    private PatientProfile findPatientByOldClientGuid(String oldClientGuid) {
        PatientProfile patient = patientProfileRepository.findByOldClientGuid(oldClientGuid);
        if (patient == null) {
            logger.warn("Patient with oldClientGuid {} not found.", oldClientGuid);
        }
        return patient;
    }

    private PatientNote findOrCreatePatientNote(Note note, CompanyUser user, PatientProfile patient) {
        PatientNote patientNote = patientNoteRepository.findByNoteGuid(note.getGuid());
        if (patientNote == null) {
            // Create a new PatientNote if it doesn't exist
            patientNote = new PatientNote();
            patientNote.setNoteGuid(note.getGuid());
            patientNote.setCreatedDateTime(parseDateTime(note.getCreatedDateTimeString()));
            patientNote.setLastModifiedDateTime(parseDateTime(note.getModifiedDateTimeString()));
            patientNote.setCreatedByUser(user);
            patientNote.setLastModifiedByUser(user);
            patientNote.setNote(note.getComments());
            patientNote.setPatient(patient);
        } else {
            // Update existing PatientNote if needed
            if (patientNote.getLastModifiedDateTime() == null ||
                    patientNote.getLastModifiedDateTime().isBefore(parseDateTime(note.getModifiedDateTimeString()))) {
                patientNote.setLastModifiedDateTime(parseDateTime(note.getModifiedDateTimeString()));
                patientNote.setNote(note.getComments());
                patientNote.setLastModifiedByUser(user);
            }
        }
        return patientNote;
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            logger.error("Error parsing date and time string: {}", dateTimeString, e);
            return null;
        }
    }
}
