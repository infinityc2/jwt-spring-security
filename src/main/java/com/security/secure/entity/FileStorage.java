package com.security.secure.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "FILE_STORAGE")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "file_storage_seq")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_storage_seq")
    private Long id;

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String fileType;

    @Lob
    @NotNull
    private byte[] data;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date updateAt;
    
}