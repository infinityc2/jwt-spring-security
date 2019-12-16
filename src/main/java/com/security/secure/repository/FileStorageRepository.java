package com.security.secure.repository;

import java.util.Optional;

import com.security.secure.entity.FileStorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FileStorageRepository extends JpaRepository<FileStorage, Long>{

    Optional<FileStorage> findByFileName(String fileName);
}