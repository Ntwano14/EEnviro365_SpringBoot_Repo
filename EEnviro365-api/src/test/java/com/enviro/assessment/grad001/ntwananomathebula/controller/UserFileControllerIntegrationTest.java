package com.enviro.assessment.grad001.ntwananomathebula.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.repository.UserFileRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserFileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserFileRepository fileRepository;

    @Test
    void testUploadFileIntegration() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());

        mockMvc.perform(multipart("/api/files/upload").file(mockFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("File uploaded successfully"));
    }

    @Test
    void testGetProcessedFileIntegration() throws Exception {
        UserFile file = new UserFile();
        file.setFileName("test.txt");
        file.setProcessedData("test content");

        file = fileRepository.save(file);

        mockMvc.perform(get("/api/files/" + file.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("test.txt"))
                .andExpect(jsonPath("$.processedData").value("test content"));
    }
}

