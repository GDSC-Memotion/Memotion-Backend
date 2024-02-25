package com.example.memotion.analysis.service;

import com.example.memotion.analysis.domain.Analysis;
import com.example.memotion.reecord.domain.Diary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnalysisService {
    private final String ANALYSIS_SERVER_URI = "http://localhost:8000";

    public Analysis getAnalysisResult(Diary savedDiary) {
        // REST API의 URL
        String url = ANALYSIS_SERVER_URI + "/emotion/text";

        // 요청 바디
        String requestBody = "{\"input\": \"" +
                savedDiary.getDescription() +
                "\"}";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔티티 설정
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // 응답 받은 데이터 출력
        String responseBody = responseEntity.getBody();
        System.out.println(responseBody);

        return null;

    }

}
