package com.example.importapp.controller;

import com.example.importapp.service.ImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ImportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ImportService importService;

    @InjectMocks
    private ImportController importController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(importController).build();
    }

    @Test
    void testImportData() throws Exception {
        doNothing().when(importService).importData();

        mockMvc.perform(post("/import")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


