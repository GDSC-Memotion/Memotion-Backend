package com.example.memotion.heealing.repository;

import com.example.memotion.heealing.domain.HealingDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealingDiaryRepository extends JpaRepository<HealingDiary, Long> {
}
