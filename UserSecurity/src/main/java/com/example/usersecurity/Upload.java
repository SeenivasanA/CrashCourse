package com.example.usersecurity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="upload")
@Data
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="file_name")
    private String fileName;
    @Column(name="file_path")
    private String filePath;
    @Column(name="file_type")
    private String fileType;
    @Lob
    @Column(name="file_data")
    private byte[] fileData;
}
