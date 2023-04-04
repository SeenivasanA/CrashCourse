package com.example.usersecurity.service.serviceImpl;

import com.example.usersecurity.Upload;
import com.example.usersecurity.repository.UploadRepo;
import com.example.usersecurity.service.FileUploadSerive;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadServiceImpl implements FileUploadSerive {

    private String uploadFilePath = "/home/seenivasan/Downloads/checking_upload/uploaded_";

    @Autowired
    private UploadRepo uploadRepo;

    @Override
    public void uploadToDb(MultipartFile file) {
        try {

            byte[] data = file.getBytes();
            Path path = Paths.get(uploadFilePath + file.getOriginalFilename());
            Files.write(path, data);

            Upload upload = new Upload();
            upload.setFileData(file.getBytes());
            upload.setFileName(file.getOriginalFilename());
            upload.setFileType(file.getContentType());
            upload.setFilePath(uploadFilePath+ file.getOriginalFilename());
            uploadRepo.save(upload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Upload downloadFile(Long fileId) {
        Upload file = uploadRepo.getOne(fileId);
        return file;
    }

    @Override
    public void uploadToLocal(MultipartFile file) {

        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(uploadFilePath + file.getOriginalFilename());
            Files.write(path, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUpload(Long id) {
        try {
            Upload file = uploadRepo.getOne(id);
            Files.delete(Path.of(file.getFilePath()));
            uploadRepo.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
