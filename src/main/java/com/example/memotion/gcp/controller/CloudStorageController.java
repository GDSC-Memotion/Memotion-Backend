package com.example.memotion.gcp.controller;

import com.example.memotion.common.dto.BaseResponse;
import com.example.memotion.gcp.service.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CloudStorageController {
    private final CloudStorageService cloudStorageService;

    @PostMapping("/cloudTest")
    public BaseResponse<String> testStorageupload(@RequestPart("images") MultipartFile images) throws IOException {
        return new BaseResponse<>(cloudStorageService.uploadMultipartFileToCloudStorage(images));
    }

}
