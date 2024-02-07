package com.example.memotion.reecord.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindDiaryDetailRes {
    private Long diaryId;
    private List<String> imageUris;
    private String description;

    private DiaryAnalysisResultDTO analysisResult;
    private String youtubeUri;
    private String youtubeMusicUri;

    @Builder
    public FindDiaryDetailRes(Long diaryId, List<String> imageUris, String description, DiaryAnalysisResultDTO analysisResult, String youtubeUri, String youtubeMusicUri) {
        this.diaryId = diaryId;
        this.imageUris = imageUris;
        this.description = description;
        this.analysisResult = analysisResult;
        this.youtubeUri = youtubeUri;
        this.youtubeMusicUri = youtubeMusicUri;
    }
}
