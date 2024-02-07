package com.example.memotion.youtube.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@Transactional
public class YoutubeService {
    //@Value 어노테이션을 사용하여 application.yml에서 정의한 YouTube API 키를 주입 받음
    @Value("${youtube.api.key}")
    private String apiKey;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, httpRequest -> {})
                .build();
    }

    public String getVideos(String query) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Search.List request = youtubeService.search()
                .list(Collections.singletonList("snippet"));

        request.setMaxResults(5L)
                .setKey(apiKey)
                .setOrder("viewCount") //sorting
                .setQ(query) // query
                .setRegionCode("US") // set region
                .setType(Collections.singletonList("video")) // video or playlist etc.
                .setVideoCaption("any"); // caption or not caption

        SearchListResponse response = request.execute();

        return response.toString();
    }
}