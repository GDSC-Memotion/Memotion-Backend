package com.example.memotion.reecord.dto;

import com.example.memotion.reecord.domain.Diary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FindDailyDiaryRes {
    private Long id;
    private String description;
    private Long memberId;
    private String mood;
    private LocalDateTime createdAt;
    private List<String> imageUris;

    public static FindDailyDiaryRes of(Diary diary, List<String> imageUris, String mood) {
        return FindDailyDiaryRes.builder()
                .id(diary.getId())
                .memberId(diary.getMember().getId())
                .imageUris(imageUris)
                .description(diary.getDescription())
                .mood(mood)
                .createdAt(diary.getCreatedAt())
                .build();
    }
    @Builder

    public FindDailyDiaryRes(Long id, String description, Long memberId, String mood, LocalDateTime createdAt, List<String> imageUris) {
        this.id = id;
        this.description = description;
        this.memberId = memberId;
        this.mood = mood;
        this.createdAt = createdAt;
        this.imageUris = imageUris;
    }
}
