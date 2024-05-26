package com.enviro.assessment.grad001.ntwananomathebula.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;
import com.enviro.assessment.grad001.ntwananomathebula.repository.UserFileRepository;

/**
* Service class for handling file operations.
*/
@Service
public class UserFileService {

	 private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB

	    @Autowired
	    private UserFileRepository fileRepository;

	    /**
	     * Save a file to the database.
	     * 
	     * @return the saved File entity
	     * @throws IOException if an I/O error occurs
	     */
	    public UserFile saveFile(MultipartFile file) throws IOException {
	        validateFile(file);

	        // Check if a file with the same name already exists
	        if (fileRepository.existsByFileName(file.getOriginalFilename())) {
	            throw new RuntimeException("File with the same name already exists: " + file.getOriginalFilename());
	        }

	        // Convert MultipartFile to File entity
	        UserFile dbFile = new UserFile();
	        dbFile.setFileName(file.getOriginalFilename());
	        dbFile.setProcessedData(processFileData(file)); // Process file content

	        return fileRepository.save(dbFile);
	    }

	    /**
	     * Retrieve a file by its ID.
	     * 
	     * @param id the ID of the file to retrieve
	     * @return the File entity
	     */
	    public UserFile getFileById(Long id) {
	        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id " + id));
	    }

	    /**
	     * Process the content of the file.
	     * 
	     * @param file the file to process
	     * @return the processed data as a string
	     * @throws IOException if an I/O error occurs
	     */
	    private String processFileData(MultipartFile file) throws IOException {
	        // Implement your file processing logic here
	        return new String(file.getBytes()); // Placeholder for actual processing logic
	    }

	    /**
	     * Validate the uploaded file.
	     * 
	     * @param file the file to validate
	     */
	    private void validateFile(MultipartFile file) {
	        if (file.getSize() > MAX_FILE_SIZE) {
	            throw new RuntimeException("File size exceeds the maximum limit of 2 MB");
	        }

	        if (!file.getContentType().equals("text/plain")) {
	            throw new RuntimeException("Invalid file type. Only text files are allowed");
	        }
	    }
	}