package com.lyc.ai.dto;

public class FileUploadResponse {
    private String fileName;
    private String description;
    private String category;
    private String tags;
    private String message;
    private String imageUrl1;  // 新增字段
    private String imageUrl2;


    public FileUploadResponse(String fileName, String description, String category, String tags, String imageUrl1, String imageUrl2) {
        this.fileName = fileName;
        this.description = description;
        this.category = category;
        this.tags = tags;
        this.message = "文件上传成功";
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
    }

    // 添加 imageUrl 的 getter 和 setter
    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
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