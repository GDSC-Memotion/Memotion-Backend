package com.example.memotion.analysis.service;

import com.example.memotion.analysis.domain.Analysis;
import com.example.memotion.analysis.domain.EmotionResponse;
import com.example.memotion.analysis.repository.AnalysisRepository;
import com.example.memotion.reecord.domain.Diary;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisService {
    @Value("${fast-api.host}")
    private String ANALYSIS_SERVER_URI;

    public Analysis getAnalysisResult(Diary savedDiary) {
        log.info("감정분석 분석 시작");
        // REST API의 URL
        String url = ANALYSIS_SERVER_URI + "/emotion";

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

        // 응답 받은 JSON 문자열
        String responseBody = responseEntity.getBody();

        // ObjectMapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        Analysis result = new Analysis();
        result.setMember(savedDiary.getMember());
        result.setDiary(savedDiary);

        try {
            // JSON 문자열을 객체로 변환
            EmotionResponse emotionResponse = objectMapper.readValue(responseBody, EmotionResponse.class);
            System.out.println(emotionResponse);
            result.setEmotions(emotionResponse);
            log.info("감정분석 완료");
        } catch (Exception e) {
            log.error("감정분석 간 에러 발생");
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
