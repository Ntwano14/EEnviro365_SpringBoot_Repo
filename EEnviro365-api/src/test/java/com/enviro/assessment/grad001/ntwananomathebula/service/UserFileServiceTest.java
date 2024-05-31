package com.enviro.assessment.grad001.ntwananomathebula.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.repository.UserFileRepository;

public class UserFileServiceTest {

    @Mock
    private UserFileRepository userFileRepository;

    @InjectMocks
    private UserFileService userFileService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveFile() throws IOException {
        // Create a mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);

        // Set up mock behavior for getOriginalFilename, getBytes, and getContentType
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenReturn("test data".getBytes());
        when(mockFile.getContentType()).thenReturn("text/plain");

        // Create a mock UserFile object
        UserFile mockUserFile = new UserFile();
        mockUserFile.setId(1L);
        mockUserFile.setFileName("test.txt");
        mockUserFile.setProcessedData("Processed data content");

        // Set up mock behavior for the save method of the userFileRepository
        when(userFileRepository.save(any(UserFile.class))).thenReturn(mockUserFile);

        // Call the saveFile method of the service
        userFileService.saveFile(mockFile);

        // Verify that the save method of the repository was called exactly once
        verify(userFileRepository, times(1)).save(any(UserFile.class));
    }

    @Test
    public void testGetFileById() {
        // Create a mock UserFile object
        UserFile mockUserFile = new UserFile();
        mockUserFile.setId(1L);
        mockUserFile.setFileName("test.txt");
        mockUserFile.setProcessedData("Processed data content");

        // Set up mock behavior for the findById method of the userFileRepository
        when(userFileRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUserFile));

        // Call the getFileById method of the service
        UserFile result = userFileService.getFileById(1L);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has the expected values
        assertEquals(1L, result.getId());
        assertEquals("test.txt", result.getFileName());
        assertEquals("Processed data content", result.getProcessedData());

        // Verify that the findById method of the repository was called exactly once
        verify(userFileRepository, times(1)).findById(1L);
    }
}
