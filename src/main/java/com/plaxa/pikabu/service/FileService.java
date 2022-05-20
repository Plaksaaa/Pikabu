package com.plaxa.pikabu.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.plaxa.pikabu.configuration.property.FirebaseProperties;
import com.plaxa.pikabu.dto.FileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FirebaseProperties firebaseProperties;

    @SneakyThrows
    public FileResponseDto upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

        File file = this.convertToFile(multipartFile, fileName);
        String url = this.uploadFile(file, fileName);
        file.deleteOnExit();
        return new FileResponseDto(url);
    }

    @SneakyThrows
    public FileResponseDto download(String fileName) {
        String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
        String destFilePath = firebaseProperties.getDownloadTo() + destFileName;

        com.google.auth.Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(firebaseProperties.getJsonPath()));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of(firebaseProperties.getBucket(), fileName));
        blob.downloadTo(Paths.get(destFilePath));
        return new FileResponseDto("Successfully Downloaded!");
    }

    @SneakyThrows
    private String uploadFile(File file, String fileName) {
        BlobId blobId = BlobId.of(firebaseProperties.getBucket(), fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(firebaseProperties.getJsonPath()));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(firebaseProperties.getDownloadUrl(), URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
