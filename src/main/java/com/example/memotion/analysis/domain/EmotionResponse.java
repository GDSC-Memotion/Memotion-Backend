package com.example.memotion.analysis.domain;

import lombok.Data;

import java.util.Arrays;

@Data
public class EmotionResponse {
    private Emotion[][] output;

    @Override
    public String toString() {
        return "EmotionResponse{" +
                "output=" + Arrays.toString(output) +
                '}';
    }
}