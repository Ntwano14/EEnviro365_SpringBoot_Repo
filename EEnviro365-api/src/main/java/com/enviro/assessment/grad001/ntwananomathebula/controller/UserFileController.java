package com.enviro.assessment.grad001.ntwananomathebula.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.service.UserFileService;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/files")
@Tag(name = "File Management", description = "API for managing file uploads and retrievals")
public class UserFileController {

    @Autowired
    private UserFileService fileService;

    /**
     * Endpoint to upload a text file for processing.
     * @param file The file to be uploaded.
     * @return ResponseEntity with a status message.
     */
    @Operation(summary = "Upload a text file", description = "Uploads a text file for processing")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File uploaded successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(example = "{\"status\":\"success\", \"message\":\"File uploaded successfully\"}"))),
        @ApiResponse(responseCode = "400", description = "Invalid file type or size",
            content = @Content(mediaType = "application/json",
                schema = @Schema(example = "{\"status\":\"error\", \"message\":\"Invalid file type or size\"}")))
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
        @Parameter(description = "The file to be uploaded", required = true)
        @RequestParam("file") MultipartFile file) {
        try {
            // Call the service to save the file
            fileService.saveFile(file);
            // Return success response
            return ResponseEntity.ok().body("{\"status\":\"success\", \"message\":\"File uploaded successfully\"}");
        } catch (Exception e) {
            // Return error response if an exception occurs
            return ResponseEntity.badRequest().body("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Endpoint to retrieve a processed file by its ID.
     * @param id The ID of the file to retrieve.
     * @return ResponseEntity with the file data or an error message.
     */
    @Operation(summary = "Get processed file by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserFile.class))),
        @ApiResponse(responseCode = "404", description = "File not found",
            content = @Content(mediaType = "application/json",
                schema = @Schema(example = "{\"status\":\"error\", \"message\":\"File not found\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProcessedFile(@PathVariable Long id) {
        // Retrieve the file by its ID
        UserFile file = fileService.getFileById(id);
        if (file != null) {
            // Return the file data if found
            return ResponseEntity.ok(file);
        } else {
            // Return an error message if the file is not found
            return ResponseEntity.status(404).body("{\"status\":\"error\", \"message\":\"File not found\"}");
        }
    }
}
