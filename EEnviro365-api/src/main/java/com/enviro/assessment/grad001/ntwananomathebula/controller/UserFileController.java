package com.enviro.assessment.grad001.ntwananomathebula.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import com.enviro.assessment.grad001.ntwananomathebula.dto.FileResponse;
import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.service.UserFileService;

/**
 * REST controller for handling file-related API requests.
 */
@RestController
@RequestMapping("/api/files")
public class UserFileController {

	    @Autowired
	    private UserFileService fileService;

	    /**
	     * Handle file upload request.
	     * 
	     * @param file the file to upload
	     * @return a ResponseEntity with the saved File entity
	     */
	    @PostMapping("/upload")
	    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	        try {
	            UserFile savedFile = fileService.saveFile(file);
	            FileResponse fileResponse = new FileResponse(savedFile);
	            return ResponseEntity.ok(createSuccessResponse("File uploaded successfully"));
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(400).body(createErrorResponse(e.getMessage()));
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(createErrorResponse("Failed to upload file"));
	        }
	    }

	    /**
	     * Retrieve processed file data by ID.
	     * 
	     * @param id the ID of the file to retrieve
	     * @return a ResponseEntity with a custom JSON response
	     */
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getProcessedFile(@PathVariable Long id) {
	        try {
	            UserFile file = fileService.getFileById(id);
	            FileResponse fileResponse = new FileResponse(file);
	            return ResponseEntity.ok(fileResponse);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(createErrorResponse(e.getMessage()));
	        }
	    }

	    /**
	     * Helper method to create a success response.
	     * 
	     * @param message the success message
	     * @return a map representing the success response
	     */
	    private Map<String, Object> createSuccessResponse(String message) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", message);
	        return response;
	    }

	    /**
	     * Helper method to create an error response.
	     * 
	     * @param message the error message
	     * @return a map representing the error response
	     */
	    private Map<String, Object> createErrorResponse(String message) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "error");
	        response.put("message", message);
	        return response;
	    }
	}