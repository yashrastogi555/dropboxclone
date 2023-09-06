package com.project.dropboxclone.controller;

import com.project.dropboxclone.entity.FileMetaData;
import com.project.dropboxclone.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/files/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadResult = fileService.uploadFileTOFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadResult);
    }

    @GetMapping("/files/{file_id}")
    public ResponseEntity<?> getFile(@PathVariable(value = "file_id") Long fileId) throws IOException {
        byte[] file = fileService.downloadFileFromFileSystem(fileId);
        if(file != null) {
            return ResponseEntity.status(HttpStatus.OK).body(file);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not found");
    }

    @PutMapping("/files/{old_file_id}")
    public ResponseEntity<?> updateFile(@RequestParam("file") MultipartFile newFile, @PathVariable(value = "old_file_id") Long oldFileId) throws IOException {
        String update = fileService.updateFile(newFile,oldFileId);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @GetMapping("/files")
    public ResponseEntity<?> getAllFiles() {
        List<FileMetaData> allFiles = fileService.getAllFiles();
        return ResponseEntity.status(HttpStatus.OK).body(allFiles.toString());
    }

    @DeleteMapping("/files/{file_id}")
    public ResponseEntity<?> deleteFileById(@PathVariable(value = "file_id") Long fileId) {
        String result =fileService.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
