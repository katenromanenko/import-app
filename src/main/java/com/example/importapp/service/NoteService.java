package com.example.importapp.service;

import com.example.importapp.model.Client;
import com.example.importapp.model.Note;
import com.example.importapp.model.NoteRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

@Service
public class NoteService {

    private final RestTemplate restTemplate;

    @Value("${notes.service.url}")
    private String notesServiceUrl;

    public NoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<Note> fetchNotes(Client client) {
        NoteRequest noteRequest = new NoteRequest(client.getAgency(), client.getGuid());
        HttpEntity<NoteRequest> requestEntity = new HttpEntity<>(noteRequest);

        try {
            ResponseEntity<List<Note>> response = restTemplate.exchange(
                    notesServiceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
