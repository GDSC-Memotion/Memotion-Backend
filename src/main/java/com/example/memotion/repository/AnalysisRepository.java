package com.example.memotion.repository;

import com.example.memotion.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
}
