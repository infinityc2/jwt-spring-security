package com.security.secure.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "FILE_STORAGE")
@SequenceGenerator(name = "file_storage_seq")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_storage_seq")
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;
}