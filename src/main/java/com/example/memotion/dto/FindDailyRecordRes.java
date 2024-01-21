package com.example.memotion.dto;

import com.example.memotion.domain.Member;
import com.example.memotion.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FindDailyRecordRes {
    private Long id;
    private String description;
    private Long memberId;
    private String mood;
    private List<String> imageUris;

    public static FindDailyRecordRes of(Record record, List<String> imageUris) {
        return FindDailyRecordRes.builder()
                .id(record.getId())
                .mood(record.getMood().toString())
                .member(record.getMember())
                .imageUris(imageUris)
                .description(record.getDescription())
                .build();
    }
    @Builder
    public FindDailyRecordRes(Long id, String description, Member member, String mood, List<String> imageUris) {
        this.id = id;
        this.description = description;
        this.memberId = member.getId();
        this.mood = mood;
        this.imageUris = imageUris;
    }
}
