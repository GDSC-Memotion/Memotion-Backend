package com.example.memotion.analysis.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Emotion {
    @JsonProperty("label")
    private String label;

    @JsonProperty("score")
    private String score;
}
