package com.plseal.zhangzu.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {
    private static final String UPLOAD_DIR = "C:\\Github\\zhangzu\\he";

    @GetMapping("/upload_index")
    public String uploadIndex() {
        return "upload_index";
    }

    @PostMapping("/upload_file_post")
    public String uploadFilePost(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "请选择文件";
        }

        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 构建文件存储路径
            String filePath = UPLOAD_DIR + File.separator + fileName;
            // 将文件保存到指定路径
            file.transferTo(new File(filePath));
            return "upload_result";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败: " + e.getMessage();
        }
    }

    @GetMapping("/uploads/{fileName}")
    public ResponseEntity<byte[]> getUploadedFile(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(UPLOAD_DIR, fileName);
        byte[] fileBytes = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}

