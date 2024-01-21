package com.example.memotion.repository;

import com.example.memotion.domain.Record;
import com.example.memotion.domain.RecordImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
    List<RecordImage> findRecordImageByRecord(Record record);
}
