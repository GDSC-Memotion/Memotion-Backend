package com.example.memotion.service;

import com.example.memotion.domain.MoodEnum;
import com.example.memotion.domain.Record;
import com.example.memotion.domain.Member;
import com.example.memotion.dto.CreateRecordReq;
import com.example.memotion.repository.RecordRepository;
import com.example.memotion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public Long addRecord(CreateRecordReq createRecordReq) {
        Member member = userRepository.findById(1L).orElseThrow(() -> new NoSuchElementException());
        Record record = Record.builder()
                .imageUrl(createRecordReq.getImageUri())
                .mood(MoodEnum.from(createRecordReq.getMood()))
                .description(createRecordReq.getDescription())
                .member(member)
                .build();

        Record save = recordRepository.save(record);
        return save.getId();
    }
}
