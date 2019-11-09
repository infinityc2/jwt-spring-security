package com.security.secure.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.security.secure.entity.FileStorage;
import com.security.secure.repository.FileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Autowired private FileStorageRepository fileStorageRepository;

    public FileStorage storage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                return null;
            }

            FileStorage fileStorage = new FileStorage();
            fileStorage.setFileName(fileName);
            fileStorage.setFileType(file.getContentType());
            fileStorage.setData(file.getBytes());
            return fileStorageRepository.save(fileStorage);
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
        
    }

    public FileStorage updateFileStorage(MultipartFile file, Long id) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                return null;
            }

            FileStorage fileStorage = fileStorageRepository.findById(id).get();
            fileStorage.setFileName(fileName);
            fileStorage.setFileType(file.getContentType());
            fileStorage.setData(file.getBytes());
            return fileStorageRepository.save(fileStorage);
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
    }

    public FileStorage getFile(Long id) throws FileNotFoundException {
        return fileStorageRepository.findById(id).orElseThrow(() -> new FileNotFoundException("File id " + id + " not found "));
    }
}