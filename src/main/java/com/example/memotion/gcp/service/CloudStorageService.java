package com.example.memotion.gcp.service;

import com.google.api.client.util.Value;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CloudStorageService {
    // The ID of your GCP project
    @Value("${cloud.gcp.storage.project_id}")
    private String PROJECT_ID;
    // The ID of your GCS bucket

    @Value("${cloud.gcp.storage.bucket_name}")
    private String BUCKET_NAME;
    private static final String TEMP_FILE_PATH = "temp/";


    public String uploadMultipartFileToCloudStorage(MultipartFile imageFile) throws IOException {
        // !!!!!!!!!!!이미지 업로드 관련 부분!!!!!!!!!!!!!!!
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = imageFile.getContentType(); // 파일의 형식 ex) JPG

        Storage storage = StorageOptions.newBuilder()
                .setProjectId(PROJECT_ID)
                .build()
                .getService();

        // Cloud에 이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, uuid)
                        .setContentType(ext)
                        .build(),
                imageFile.getInputStream()
        );

        return "https://storage.googleapis.com/" +
                "memotion/" +
                uuid;
    }
}
