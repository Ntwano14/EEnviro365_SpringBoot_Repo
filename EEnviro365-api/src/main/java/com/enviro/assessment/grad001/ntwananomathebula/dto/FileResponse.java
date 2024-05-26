package com.enviro.assessment.grad001.ntwananomathebula.dto;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;

/**
 * DTO class for File response.
 */
public class FileResponse {

    private Long id;
    private String fileName;
    private String processedData;

    // Constructor
    public FileResponse(UserFile file) {
        this.id = file.getId();
        this.fileName = file.getFileName();
        this.processedData = file.getProcessedData();
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
}