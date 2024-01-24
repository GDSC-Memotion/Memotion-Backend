package com.example.memotion.repository;

import com.example.memotion.domain.Record;
import com.example.memotion.dto.DailyEmotionAvgDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT new com.example.memotion.dto.DailyEmotionAvgDTO(FORMAT(r.createdAt, 'yyyy-MM-dd'), AVG(a.joy), AVG(a.neutral), AVG(a.sadness), AVG(a.surprise), AVG(a.anger), AVG(a.fear), AVG(a.disgust)) " +
            "FROM Analysis a JOIN a.record r " +
            "WHERE r.createdAt >= :start AND r.createdAt < :end " +
            "GROUP BY FORMAT(r.createdAt, 'yyyy-MM-dd') ")
    List<DailyEmotionAvgDTO> findRecordByCalendar(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
