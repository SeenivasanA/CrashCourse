package com.example.usersecurity.service;

import com.example.usersecurity.Upload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Component
public interface FileUploadSerive {
    public void uploadToLocal(MultipartFile file);
    public void uploadToDb(MultipartFile file);
    public Upload downloadFile(Long fileId);
    public void deleteUpload(Long id);
}
