package com.project.dropboxclone.service;


import com.project.dropboxclone.entity.FileMetaData;
import com.project.dropboxclone.repository.FileMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class FileService {

    private final String FOLDER_PATH = "C:\\Users\\YASH\\Desktop\\DROPBOXCLONE";

    @Autowired
    private FileMetaDataRepository repository;


public String uploadFileTOFileSystem(MultipartFile file) throws IOException {

    String filePath = FOLDER_PATH+ file.getOriginalFilename();

    FileMetaData data = repository.save(FileMetaData.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
//            .data(file.getBytes())
            .date(new Date())
            .filepath(filePath)
            .build());
    if (data != null) {
        file.transferTo(new File(filePath));
        return "file uploaded successfully : " + file.getOriginalFilename() +"at " +filePath;
    }
    return "file upload failed";
}

    public byte[] downloadFileFromFileSystem(long id) throws IOException {
        Optional<FileMetaData> fileMetaData = repository.findById(id);
        if (fileMetaData.isPresent()) {
            String filePath = FOLDER_PATH + fileMetaData.get().getName();
            byte[] fileData = Files.readAllBytes(new File(filePath).toPath());
            return fileData;
        }
        return null;
    }

    public String deleteFile(long id)
    {
        Optional<FileMetaData> fileMetaData = repository.findById(id);
        String result ;
        if(fileMetaData.isPresent())
        {
            repository.deleteById(id);
            result = "Deleted successfully ";
            return result;
        }
        result = "File deletion failed";
        return result;
    }

    public String updateFile(MultipartFile file, long oldFileId) throws IOException {
        String uploadNewFile =  uploadFileTOFileSystem(file);
        if(uploadNewFile.contains("successfully"))
        {
            String s=  deleteFile(oldFileId);
            return "Updated to new file";
        }
        return "Failed to update file ";
    }

    public List<FileMetaData> getAllFiles()
    {
        return repository.findAll();
    }

}
