package com.enviro.assessment.grad001.ntwananomathebula.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

/**
 * Entity class representing a File.
 */
@Entity
public class UserFile {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the uploaded file
    private String fileName;

    // Processed data from the file
    @Lob
    private String processedData;
    
    // Default Constructor
    public UserFile() {
	
	}

    // Constructor with fields
    public UserFile(Long id, String fileName, String processedData) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.processedData = processedData;
	}

	// Getters and Setters
    public Long getId() {
        return id;
    }


	public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProcessedData() {
        return processedData;
    }

    public void setProcessedData(String processedData) {
        this.processedData = processedData;
    }

	public void setFileType(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setFileContent(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

}