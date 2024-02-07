package com.example.memotion.reecord.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryAnalysisResultDTO {
    private String emotion;
    private double joy;
    private double neutral;
    private double sadness;
    private double surprise;
    private double anger;
    private double fear;
    private double disgust;

    @Builder
    public DiaryAnalysisResultDTO(String emotion, double joy, double neutral, double sadness, double surprise, double anger, double fear, double disgust) {
        this.emotion = emotion;
        this.joy = joy;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
        this.anger = anger;
        this.fear = fear;
        this.disgust = disgust;
    }
}
