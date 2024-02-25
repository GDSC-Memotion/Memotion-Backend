package com.example.memotion.analysis.repository;

import com.example.memotion.analysis.domain.Analysis;
import com.example.memotion.reecord.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    public Analysis findAnalysisByDiary(Diary diary);
}
