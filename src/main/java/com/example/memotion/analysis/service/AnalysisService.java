package com.example.memotion.analysis.service;

import com.example.memotion.analysis.domain.Analysis;
import com.example.memotion.reecord.domain.Diary;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    private final String ANALYSIS_SERVER_URI = "http://localhost:8000";

    public Analysis getAnalysisResult(Diary savedDiary) {
        return null;
    }

}
