package com.example.memotion.reecord.repository;

import com.example.memotion.reecord.domain.Diary;
import com.example.memotion.reecord.dto.DailyEmotionAvgDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query(value = "SELECT new com.example.memotion.reecord.dto.DailyEmotionAvgDTO(FUNCTION('DATE_FORMAT', d.createdAt, '%Y-%m-%d'), AVG(a.joy), AVG(a.neutral), AVG(a.sadness), AVG(a.surprise), AVG(a.anger), AVG(a.fear), AVG(a.disgust)) " +
            "FROM Analysis a JOIN a.diary d " +
            "WHERE d.createdAt >= :start AND d.createdAt < :end AND d.status = 'ACTIVATE'" +
            "GROUP BY FUNCTION('DATE_FORMAT', d.createdAt, '%Y-%m-%d') ")
    List<DailyEmotionAvgDTO> findDiaryByCalendar(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Override
    @Query(value = "SELECT d FROM Diary d WHERE d.status = 'ACTIVATE' AND d.id = :id")
    Optional<Diary> findById(@Param("id") Long id);
}
