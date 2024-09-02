package com.example.importapp.service;

import com.example.importapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final RestTemplate restTemplate;

    @Value("${old.system.url}")
    private String oldSystemUrl;

    @Autowired
    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Client> fetchClients() {
        try {
            ResponseEntity<List<Client>> response = restTemplate.exchange(
                    oldSystemUrl + "/clients",
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody() != null ? response.getBody() : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

