package com.enviro.assessment.grad001.ntwananomathebula.controller;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.service.UserFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserFileControllerTest {

    @Mock
    private UserFileService fileService;  // Mock instance of UserFileService

    @InjectMocks
    private UserFileController userFileController;  // Inject mocks into UserFileController

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mock objects
    }

    /**
     * Test successful upload of a file.
     * This method verifies that when a valid file is uploaded, the service is called and a success response is returned.
     */
    @Test
    void testUploadFile_Success() throws Exception {
        // Create a mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content".getBytes());

        // Mock the saveFile method to do nothing
        doNothing().when(fileService).saveFile(file);

        // Call the controller method
        ResponseEntity<?> response = userFileController.uploadFile(file);

        // Verify the response
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"status\":\"success\", \"message\":\"File uploaded successfully\"}", response.getBody());
    }

    /**
     * Test failed upload of a file.
     * This method verifies that when an exception occurs during file upload, an error response is returned.
     */
    @Test
    void testUploadFile_Failure() throws Exception {
        // Create a mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content".getBytes());

        // Mock the saveFile method to throw an exception
        doThrow(new RuntimeException("File upload failed")).when(fileService).saveFile(file);

        // Call the controller method
        ResponseEntity<?> response = userFileController.uploadFile(file);

        // Verify the response
        assertEquals(400, response.getStatusCode());
        assertEquals("{\"status\":\"error\", \"message\":\"File upload failed\"}", response.getBody());
    }

    /**
     * Test successful retrieval of a file by ID.
     * This method verifies that when a file with a given ID exists, it is retrieved successfully.
     */
    @Test
    void testGetProcessedFile_Success() {
        Long fileId = 1L;  // ID of the file to retrieve

        // Create a mock UserFile
        UserFile userFile = new UserFile();
        userFile.setId(fileId);
        userFile.setFileName("test.txt");
        userFile.setFileType("text/plain");
        userFile.setFileContent("Test content".getBytes());

        // Mock the getFileById method to return the mock UserFile
        when(fileService.getFileById(fileId)).thenReturn(userFile);

        // Call the controller method
        ResponseEntity<?> response = userFileController.getProcessedFile(fileId);

        // Verify the response
        assertEquals(200, response.getStatusCode());
        assertEquals(userFile, response.getBody());
    }

    /**
     * Test retrieval of a file by ID when the file is not found.
     * This method verifies that when a file with a given ID does not exist, a 404 error response is returned.
     */
    @Test
    void testGetProcessedFile_NotFound() {
        Long fileId = 1L;  // ID of the file to retrieve

        // Mock the getFileById method to return null (file not found)
        when(fileService.getFileById(fileId)).thenReturn(null);

        // Call the controller method
        ResponseEntity<?> response = userFileController.getProcessedFile(fileId);

        // Verify the response
        assertEquals(404, response.getStatusCode());
        assertEquals("{\"status\":\"error\", \"message\":\"File not found\"}", response.getBody());
    }
}
