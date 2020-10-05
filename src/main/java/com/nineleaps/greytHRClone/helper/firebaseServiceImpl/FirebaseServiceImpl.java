package com.nineleaps.greytHRClone.helper.firebaseServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import com.nineleaps.greytHRClone.dto.FirebaseCredentialDTO;
import com.nineleaps.greytHRClone.exception.ResourceNotFoundException;
import com.nineleaps.greytHRClone.helper.FirebaseService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

//@EnableAsync
@Service
public class FirebaseServiceImpl implements FirebaseService {
    private final Logger log = LoggerFactory.getLogger(FirebaseServiceImpl.class);
    private final Environment environment;

    private StorageOptions storageOptions;
    private String bucketName;
    private String projectId;

    public FirebaseServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void initializeFirebase() throws Exception {
        bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
        projectId = environment.getRequiredProperty("FIREBASE_PROJECT_ID");

        InputStream firebaseCredential = createFirebaseCredential();
        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(firebaseCredential)).build();

    }

//    @Async
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        log.debug("bucket name====" + bucketName);
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        String objectName = generateFileName(multipartFile);

        Storage storage = storageOptions.getService();

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));

        log.info("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
//        String[] uploadedFile =new String[]{"fileUrl", objectName};
//        String fileDownloadUri = uploadedFile[0];
//        String fileName = uploadedFile[1];
//        return new FileDTO(
//                fileName,
//                multipartFile.getContentType(),
//                fileDownloadUri, multipartFile.getSize());
        return objectName;
    }


    @Override
    public ResponseEntity<Object> downloadFile(String fileName, HttpServletRequest request){
        try {
            Storage storage = storageOptions.getService();

            Blob blob = storage.get(BlobId.of(bucketName, fileName));
            ReadChannel reader = blob.reader();
            InputStream inputStream = Channels.newInputStream(reader);

            byte[] content = null;
            log.info("File downloaded successfully.");

            content = IOUtils.toByteArray(inputStream);


            final ByteArrayResource byteArrayResource = new ByteArrayResource(content);

            return ResponseEntity
                    .ok()
                    .contentLength(content.length)
                    .header("Content-type", "application/octet-stream")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(byteArrayResource);
        }catch (Exception e){
            throw new ResourceNotFoundException("please enter a valid file name");
        }

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

    private InputStream createFirebaseCredential() throws Exception {
        //private key
        String privateKey = environment.getRequiredProperty("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

        FirebaseCredentialDTO firebaseCredentialDTO = new FirebaseCredentialDTO();
        firebaseCredentialDTO.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
        firebaseCredentialDTO.setProject_id(projectId);
        firebaseCredentialDTO.setPrivate_key_id("FIREBASE_PRIVATE_KEY_ID");
        firebaseCredentialDTO.setPrivate_key(privateKey);
        firebaseCredentialDTO.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
        firebaseCredentialDTO.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
        firebaseCredentialDTO.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
        firebaseCredentialDTO.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
        firebaseCredentialDTO.setAuth_provider_x509_cert_url(environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
        firebaseCredentialDTO.setClient_x509_cert_url(environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));

        //serialize with Jackson
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(firebaseCredentialDTO);

        //convert jsonString string to InputStream using Apache Commons
        return IOUtils.toInputStream(jsonString);
    }
}
