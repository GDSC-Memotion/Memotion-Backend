package com.example.memotion.repository;

import com.example.memotion.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
