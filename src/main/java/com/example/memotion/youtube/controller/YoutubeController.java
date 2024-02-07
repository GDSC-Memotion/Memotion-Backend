package com.example.memotion.youtube.controller;

import com.example.memotion.common.dto.BaseResponse;
import com.example.memotion.youtube.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
public class YoutubeController {
    private final YoutubeService youtubeService;

    @GetMapping("/api/youtube")
    public BaseResponse<String> getYoutube(@RequestParam("search") String search) throws GeneralSecurityException, IOException {
        System.out.println(search);
        return new BaseResponse<>(youtubeService.getVideos(search));

    }
}
