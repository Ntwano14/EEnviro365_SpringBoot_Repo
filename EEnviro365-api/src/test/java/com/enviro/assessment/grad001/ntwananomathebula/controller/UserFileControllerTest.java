package com.enviro.assessment.grad001.ntwananomathebula.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.service.UserFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserFileControllerTest {

    @Mock
    private UserFileService fileService;

    @InjectMocks
    private UserFileController fileController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void testUploadFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());

        UserFile file = new UserFile();
        file.setFileName("test.txt");
        file.setProcessedData("test content");

        when(fileService.saveFile(any(MockMultipartFile.class))).thenReturn(file);

        mockMvc.perform(multipart("/api/files/upload").file(mockFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("File uploaded successfully"));
    }

    @Test
    void testGetProcessedFile() throws Exception {
    	UserFile file = new UserFile();
        file.setId(1L);
        file.setFileName("test.txt");
        file.setProcessedData("test content");

        when(fileService.getFileById(1L)).thenReturn(file);

        mockMvc.perform(get("/api/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("test.txt"))
                .andExpect(jsonPath("$.processedData").value("test content"));
    }
}
