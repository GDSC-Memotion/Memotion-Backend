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
}
