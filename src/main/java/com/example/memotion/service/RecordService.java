package com.example.memotion.service;

import com.example.memotion.domain.Image;
import com.example.memotion.domain.MoodEnum;
import com.example.memotion.domain.Record;
import com.example.memotion.domain.Member;
import com.example.memotion.domain.RecordImage;
import com.example.memotion.dto.CreateRecordReq;
import com.example.memotion.dto.FindDailyRecordRes;
import com.example.memotion.repository.ImageRepository;
import com.example.memotion.repository.RecordImageRepository;
import com.example.memotion.repository.RecordRepository;
import com.example.memotion.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final RecordImageRepository recordImageRepository;

    public Long addRecord(CreateRecordReq createRecordReq) {
        Member member = memberRepository.findById(1L).orElseThrow(() -> new NoSuchElementException());
        Record record = Record.builder()
                .mood(MoodEnum.from(createRecordReq.getMood()))
                .description(createRecordReq.getDescription())
                .member(member)
                .build();
        List<String> imageUris = createRecordReq.getImageUris().stream()
                .map(e -> e.toString()).toList();

        List<Image> images = imageUris.stream()
                .map(Image::new)
                .toList();

        imageRepository.saveAll(images);
        for (Image image : images) {
            recordImageRepository.save(new RecordImage(record, image));
        }
        Record save = recordRepository.save(record);
        return save.getId();
    }

    public List<FindDailyRecordRes> findDailyRecord(String period) {
        List<FindDailyRecordRes> result = new ArrayList<>();
        LocalDate localdate = periodToLocalDate(period);
        log.debug(localdate.toString());
        List<Record> dailyRecords = recordRepository.findAll().stream()
                .filter(record -> localdate.isEqual(record.getCreatedAt().toLocalDate()))
                .toList();
        for (Record record: dailyRecords) {
            List<RecordImage> recordImageByRecord = recordImageRepository.findRecordImageByRecord(record);
            List<String> imageUris = recordImageByRecord.stream()
                    .map(recordImage ->
                            recordImage.getImage().getUri()).toList();
            result.add(FindDailyRecordRes.of(record, imageUris));
        }
        return result;
    }

    private LocalDate periodToLocalDate(String period) {
        return LocalDate.of(2000 + Integer.parseInt(period.substring(0, 2)),
                Integer.parseInt(period.substring(2, 4)),
                Integer.parseInt(period.substring(4, 6)));
    }
}
