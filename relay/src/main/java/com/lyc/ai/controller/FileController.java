package com.lyc.ai.controller;

import com.lyc.ai.Config.FileStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.HashMap;
import com.lyc.ai.dto.FileUploadResponse;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tags", required = false) String tags,
            HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "文件为空！");
            }});
        }

        try {
            // 生成唯一文件名
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(fileStorageConfig.getUploadDir(), fileName);

            // 保存文件
            Files.copy(file.getInputStream(), filePath);

            // 构建图片URL
            String imageUrl = String.format("%s://%s:%d/api/files/download/%s",
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    fileName);

            // 返回 JSON 格式的响应
            return ResponseEntity.ok(new FileUploadResponse(fileName, description, category, tags, imageUrl, ""));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new HashMap<String, String>() {{
                put("error", "文件上传失败：" + e.getMessage());
            }});
        }
    }

    @PostMapping("/upload2image")
    public ResponseEntity<?> upload2Image(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tags", required = false) String tags,
            HttpServletRequest request) {
        if (file1.isEmpty() || file2.isEmpty()) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "文件为空！");
            }});
        }

        try {
            // 生成唯一文件名
            String fileName1 = UUID.randomUUID() + "_" + file1.getOriginalFilename();
            Path filePath1 = Paths.get(fileStorageConfig.getUploadDir(), fileName1);

            // 生成唯一文件名
            String fileName2 = UUID.randomUUID() + "_" + file2.getOriginalFilename();
            Path filePath2 = Paths.get(fileStorageConfig.getUploadDir(), fileName2);

            // 如果执行到这一步报错，那么是路径不存在，因为filePath1在线上环境是Linux路径，本地运行需要切换为mac的路径
            // 保存文件
            Files.copy(file1.getInputStream(), filePath1);
            // 保存文件
            Files.copy(file2.getInputStream(), filePath2);

            // 构建图片URL
            String imageUrl1 = String.format("%s://%s:%d/api/files/download/%s",
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    fileName1);
            String imageUrl2 = String.format("%s://%s:%d/api/files/download/%s",
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    fileName2);

            // 返回 JSON 格式的响应
            return ResponseEntity.ok(new FileUploadResponse(fileName1, description, category, tags, imageUrl1,imageUrl2));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new HashMap<String, String>() {{
                put("error", "文件上传失败：" + e.getMessage());
            }});
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(fileStorageConfig.getUploadDir(), fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 根据文件类型设置 MediaType
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}