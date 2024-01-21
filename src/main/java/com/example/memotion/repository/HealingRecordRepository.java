package com.example.memotion.repository;

import com.example.memotion.domain.HealingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealingRecordRepository extends JpaRepository<HealingRecord, Long> {
}
