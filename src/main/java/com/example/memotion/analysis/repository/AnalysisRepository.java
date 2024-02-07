package com.example.memotion.analysis.repository;

import com.example.memotion.analysis.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
}
