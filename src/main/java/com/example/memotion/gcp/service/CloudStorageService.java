package com.example.memotion.gcp.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class CloudStorageService {

//    private final Storage storage;
    // The ID of your GCP project
    @Value("${gcp.project.id}")
    private String PROJECT_ID;
//    // The ID of your GCS bucket
//
    @Value("${gcp.bucket.name}")
    private String BUCKET_NAME;
//
    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    private final String GCS_API_URI = "https://storage.googleapis.com/";

    public String uploadMultipartFileToCloudStorage(MultipartFile imageFile) throws IOException {
        // !!!!!!!!!!!이미지 업로드 관련 부분!!!!!!!!!!!!!!!
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = imageFile.getContentType(); // 파일의 형식 ex) JPG
        InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
        StorageOptions options = StorageOptions.newBuilder().setProjectId(PROJECT_ID)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        Storage storage = options.getService();

        // Cloud에 이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder("memotion", uuid)
                        .setContentType(ext)
                        .build(),
                imageFile.getInputStream()
        );

        return GCS_API_URI +
                BUCKET_NAME + "/" +
                uuid;
    }
}
