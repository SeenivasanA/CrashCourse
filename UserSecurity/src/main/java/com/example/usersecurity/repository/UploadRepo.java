package com.example.usersecurity.repository;

import com.example.usersecurity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepo extends JpaRepository<Upload, Long> {
}
