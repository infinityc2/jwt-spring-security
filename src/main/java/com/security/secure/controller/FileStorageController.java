package com.security.secure.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.security.secure.entity.FileStorage;
import com.security.secure.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/files")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        FileStorage fileStorage = fileStorageService.storage(file);
        Map<String, Object> fileResponse = new HashMap<>();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
                .path(fileStorage.getId().toString()).toUriString();
        fileResponse.put("filename", fileStorage.getFileName());
        fileResponse.put("uri", fileDownloadUri);
        fileResponse.put("content-type", file.getContentType());
        fileResponse.put("size", file.getSize());
        return fileResponse;
    }

    @PostMapping("/uploads")
    public List<?> uploads(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> upload(file)).collect(Collectors.toList());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) throws Exception {
        FileStorage fileStorage = fileStorageService.getFile(id);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileStorage.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileStorage.getFileName() + "\"")
                .body(new ByteArrayResource(fileStorage.getData()));
    }

    @GetMapping("/file-storages/{id}")
    public FileStorage getFileStorage(@PathVariable Long id) throws IOException {
        return fileStorageService.getFile(id);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<InputStreamResource> displayImage(@PathVariable Long id) throws FileNotFoundException {
        FileStorage fileStorage = fileStorageService.getFile(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileStorage.getFileType()))
                .body(new InputStreamResource(new ByteArrayInputStream(fileStorage.getData())));
    }

    @PutMapping("/file-storage/{id}")
    public FileStorage updateFileStorage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return fileStorageService.updateFileStorage(file, id);
    }

}