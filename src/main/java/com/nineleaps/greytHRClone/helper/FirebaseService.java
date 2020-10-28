package com.nineleaps.greytHRClone.helper;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

import static com.nineleaps.greytHRClone.common.Constants.*;

@Service
public class FirebaseService {

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String objectName = generateFileName(multipartFile);

        FileInputStream serviceAccount = new FileInputStream(FIREBASE_SDK_JSON);
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();

        Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setProjectId(FIREBASE_PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(FIREBASE_BUCKET, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();

        storage.create(blobInfo, Files.readAllBytes(filePath));

        return objectName;
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }





}
