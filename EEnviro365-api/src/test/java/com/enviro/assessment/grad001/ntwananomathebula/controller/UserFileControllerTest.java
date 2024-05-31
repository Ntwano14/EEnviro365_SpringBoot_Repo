package com.enviro.assessment.grad001.ntwananomathebula.controller;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.service.UserFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserFileController.class)
public class UserFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFileService userFileService;

    private MockMultipartFile mockFile;

    @BeforeEach
    void setUp() {
        // Create a mock file to be used in tests
        mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is a test file".getBytes()
        );
    }

    @Test
    void testUploadFileSuccess() throws Exception {
        // Mock the saveFile method to return a new UserFile object for successful upload
        UserFile userFile = new UserFile();
        Mockito.when(userFileService.saveFile((MultipartFile) Mockito.any(MultipartFile.class))).thenReturn(userFile);

        // Perform a POST request to upload the file
        mockMvc.perform(multipart("/api/files/upload")
                .file(mockFile))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"status\":\"success\"")));
    }

    @Test
    void testUploadFileInvalidType() throws Exception {
        // Create a mock file with an invalid content type
        MockMultipartFile invalidFile = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.APPLICATION_PDF_VALUE,
                "This is a test file".getBytes()
        );

        // Mock the saveFile method to throw an exception for invalid file type
        Mockito.when(userFileService.saveFile(Mockito.any(MultipartFile.class)))
                .thenThrow(new IllegalArgumentException("Invalid file type or size"));

        // Perform a POST request with the invalid file
        mockMvc.perform(multipart("/api/files/upload")
                .file(invalidFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"status\":\"error\"")));
    }

    @Test
    void testUploadFileExceedsSizeLimit() throws Exception {
        // Create a large file that exceeds the size limit
        byte[] largeFileContent = new byte[2 * 1024 * 1024 + 1]; // 2 MB + 1 byte
        MockMultipartFile largeFile = new MockMultipartFile(
                "file",
                "large_test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                largeFileContent
        );

        // Mock the saveFile method to throw an exception for file size limit exceeded
        Mockito.when(userFileService.saveFile(Mockito.any(MultipartFile.class)))
                .thenThrow(new IllegalArgumentException("Invalid file type or size"));

        // Perform a POST request with the large file
        mockMvc.perform(multipart("/api/files/upload")
                .file(largeFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"status\":\"error\"")));
    }

    @Test
    void testGetProcessedFileSuccess() throws Exception {
        // Create a mock UserFile object
        UserFile mockUserFile = new UserFile();
        mockUserFile.setId(1L);
        mockUserFile.setFileName("test.txt");
        mockUserFile.setProcessedData("Processed data");

        // Mock the getFileById method to return the mock UserFile
        Mockito.when(userFileService.getFileById(1L)).thenReturn(mockUserFile);

        // Perform a GET request to retrieve the file
        mockMvc.perform(get("/api/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fileName").value("test.txt"))
                .andExpect(jsonPath("$.processedData").value("Processed data"));
    }

    @Test
    void testGetProcessedFileNotFound() throws Exception {
        // Mock the getFileById method to return null
        Mockito.when(userFileService.getFileById(1L)).thenReturn(null);

        // Perform a GET request to retrieve the file
        mockMvc.perform(get("/api/files/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("\"status\":\"error\"")));
    }
}
