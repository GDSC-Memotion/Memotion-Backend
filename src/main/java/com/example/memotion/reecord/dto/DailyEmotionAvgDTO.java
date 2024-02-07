package com.example.memotion.reecord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
public class DailyEmotionAvgDTO {
    private String createDate;
    private double joyAvg;
    private double neutralAvg;
    private double sadnessAvg;
    private double surpriseAvg;
    private double angerAvg;
    private double fearAvg;
    private double disgustAvg;

    public String getMaxEmotionName() {
        Map<Double, String> emotionRateMap = new HashMap<>();
        emotionRateMap.put(joyAvg, "joy");
        emotionRateMap.put(neutralAvg, "neutral");
        emotionRateMap.put(sadnessAvg, "sadness");
        emotionRateMap.put(surpriseAvg, "surprise");
        emotionRateMap.put(angerAvg, "anger");
        emotionRateMap.put(fearAvg, "fear");
        emotionRateMap.put(disgustAvg, "disgust");

        Set<Double> doubles = emotionRateMap.keySet();
        Double maxKey = doubles.stream().max(Comparator.naturalOrder()).get();

        return emotionRateMap.get(maxKey);
    }
}
