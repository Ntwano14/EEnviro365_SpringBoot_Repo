package com.enviro.assessment.grad001.ntwananomathebula.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.repository.UserFileRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public class UserFileServiceTest {

    @Mock
    private UserFileRepository fileRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private UserFileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFile() throws IOException {
        when(multipartFile.getOriginalFilename()).thenReturn("air_quality.txt");
        when(multipartFile.getBytes()).thenReturn("air_quality content".getBytes());

        UserFile file = new UserFile();
        file.setFileName("air_quality.txt");
        file.setProcessedData("air_quality content");

        when(fileRepository.save(any(UserFile.class))).thenReturn(file);

        UserFile savedFile = fileService.saveFile(multipartFile);

        assertNotNull(savedFile);
        assertEquals("air_quality.txt", savedFile.getFileName());
    }

    @Test
    void testGetFileById() {
        UserFile file = new UserFile();
        file.setId(1L);
        file.setFileName("air_quality.txt");
        file.setProcessedData("air_quality content");

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        UserFile retrievedFile = fileService.getFileById(1L);

        assertNotNull(retrievedFile);
        assertEquals("air_quality.txt", retrievedFile.getFileName());
    }
}
