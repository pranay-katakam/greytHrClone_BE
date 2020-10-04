package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.helper.FirebaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "cloud storage controler", description = "Controller class deals with downloading and uploading data into a cloud service")
@RestController
public class CloudStorageController {
    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        String uploadedFile = firebaseService.uploadFile(file);
        return  ResponseEntity.status(HttpStatus.CREATED).body(uploadedFile);
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return  firebaseService.downloadFile(fileName, request);
    }
}
