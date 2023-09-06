package com.project.dropboxclone.repository;

import com.project.dropboxclone.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
    Optional<FileMetaData> findByName(String fileName);
    List<FileMetaData> findAll();
    Optional<FileMetaData> findById(long  id);
}
