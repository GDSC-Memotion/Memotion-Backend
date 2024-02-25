package com.example.memotion.analysis.domain;

import com.example.memotion.common.domain.BaseEntity;
import com.example.memotion.member.domain.Member;
import com.example.memotion.reecord.domain.Diary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analysis")
public class Analysis extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(nullable = false)
    private float joy;
    private float neutral;
    private float sadness;
    private float surprise;
    private float anger;
    private float fear;
    private float disgust;

    public void setEmotions(EmotionResponse emotionResponse) {
        Emotion[][] outputs = emotionResponse.getOutput();
        Map<String, Float> emotionValues = new HashMap<>();
        for (Emotion emotion : outputs[0]) {
            String label = emotion.getLabel();
            String score = emotion.getScore();
            emotionValues.put(label, Float.parseFloat(score));
        }
        this.joy = emotionValues.get("joy");
        this.neutral = emotionValues.get("neutral");
        this.sadness = emotionValues.get("sadness");
        this.surprise = emotionValues.get("surprise");
        this.anger = emotionValues.get("anger");
        this.fear = emotionValues.get("fear");
        this.disgust = emotionValues.get("disgust");
    }
}
