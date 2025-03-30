package com.lyc.ai.dto;

public class FileUploadResponse {
    private String fileName;
    private String description;
    private String category;
    private String tags;
    private String message;

    public FileUploadResponse(String fileName, String description, String category, String tags) {
        this.fileName = fileName;
        this.description = description;
        this.category = category;
        this.tags = tags;
        this.message = "文件上传成功";
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}