package com.example.memotion.reecord.service;

import com.example.memotion.common.domain.STATUS;
import com.example.memotion.common.exception.NotFoundDiaryException;
import com.example.memotion.image.domain.Image;
import com.example.memotion.image.repository.ImageRepository;
import com.example.memotion.member.domain.Member;
import com.example.memotion.member.repository.MemberRepository;
import com.example.memotion.reecord.domain.Diary;
import com.example.memotion.reecord.dto.CreateDiaryReq;
import com.example.memotion.reecord.dto.CreateDiaryRes;
import com.example.memotion.reecord.dto.DailyEmotionAvgDTO;
import com.example.memotion.reecord.dto.DeleteDiaryRes;
import com.example.memotion.reecord.dto.DiaryAnalysisResultDTO;
import com.example.memotion.reecord.dto.FindCalendarDiaryRes;
import com.example.memotion.reecord.dto.FindDailyDiaryRes;
import com.example.memotion.reecord.dto.FindDiaryDetailRes;
import com.example.memotion.reecord.dto.ModifyDiaryReq;
import com.example.memotion.reecord.dto.ModifyDiaryRes;
import com.example.memotion.reecord.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

    public CreateDiaryRes addDiary(CreateDiaryReq createDiaryReq) {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new NoSuchElementException());
        LocalDateTime createdAt = stringTime2LocalDateTime(createDiaryReq.getTime());
        Diary diary = Diary.builder()
                .description(createDiaryReq.getDescription())
                .member(member)
                .createdAt(createdAt)
                .build();
        List<String> imageUris = createDiaryReq.getImageUris().stream()
                .map(e -> e.toString()).toList();

        Diary savedDiary = diaryRepository.save(diary);

        imageUris.stream()
                .map(uri -> new Image(uri, savedDiary))
                .forEach(image -> imageRepository.save(image));

        //TODO : 파이썬 서버와의 통신 및 분석 결과 저장 후 반환해야 함
        return new CreateDiaryRes(savedDiary.getId(), "anger");
    }

    private LocalDateTime stringTime2LocalDateTime(String time) {
        //Time Structure : YYYY.MM.DD MON HH:MM:SS
        String[] splitTime = time.split(" ");
        List<Integer> dates = Arrays.stream(splitTime[0].split("\\."))
                .map(Integer::parseInt)
                .toList();
        List<Integer> times = Arrays.stream(splitTime[2].split(":"))
                .map(Integer::parseInt)
                .toList();
        return LocalDateTime.of(dates.get(0),dates.get(1),dates.get(2),times.get(0),times.get(1),times.get(2));
    }

    @Transactional(readOnly = true)
    public List<FindDailyDiaryRes> findDailyDiary(String period) {
        List<FindDailyDiaryRes> result = new ArrayList<>();
        LocalDate localdate = periodToLocalDate(period);
        log.debug(localdate.toString());
        List<Diary> dailyDiaries = diaryRepository.findAll().stream()
                .filter(diary -> localdate.isEqual(diary.getCreatedAt().toLocalDate()))
                .toList();
        for (Diary diary : dailyDiaries) {
            List<String> imageUris = diary.getImages().stream()
                    .map(diaryImage ->
                            diaryImage.getUri()).toList();
            result.add(FindDailyDiaryRes.of(diary, imageUris));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public FindCalendarDiaryRes findCalendarDiary(String period) {
        final int dateCapacity = 32;

        LocalDateTime localDateTime = periodToLocalDateTime(period);
        List<DailyEmotionAvgDTO> dailyEmotionAvgs = diaryRepository.findDiaryByCalendar(localDateTime, localDateTime.plusMonths(1));
        log.info(dailyEmotionAvgs.toString());

        Map<Integer, String> emotions = new HashMap<>();
        log.info("size: " + emotions.size());

        for (DailyEmotionAvgDTO dailyEmotionAvgDTO : dailyEmotionAvgs) {
            String day = dailyEmotionAvgDTO.getCreateDate().split("-")[2];
            String maxEmotionName = dailyEmotionAvgDTO.getMaxEmotionName();
            emotions.put(Integer.parseInt(day), maxEmotionName);
        }

        return new FindCalendarDiaryRes(emotions);
    }

    @Transactional(readOnly = true)
    public FindDiaryDetailRes findDetailDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(NotFoundDiaryException::new);

        List<Image> diaryImages = diary.getImages();
        List<String> imageUris = diaryImages.stream()
                .map(Image::getUri)
                .toList();

        //TODO : 로직 수정 필요
        DiaryAnalysisResultDTO diaryAnalysisResultDTO = DiaryAnalysisResultDTO.builder()
                .joy(0.0)
                .anger(100.0)
                .fear(0.0)
                .neutral(0.0)
                .disgust(0.0)
                .surprise(0.0)
                .sadness(0.0)
                .emotion("anger")
                .build();

        // TODO : 유튜브 API 수정 필요
        return FindDiaryDetailRes.builder()
                .diaryId(diary.getId())
                .description(diary.getDescription())
                .imageUris(imageUris)
                .analysisResult(diaryAnalysisResultDTO)
                .youtubeUri("https://www.youtube.com/watch?v=BBdC1rl5sKY&pp=ygUa7Jyk7ZWYIOyCrOqxtOydmCDsp4Dtj4nshKA%3D")
                .youtubeMusicUri("https://music.youtube.com/watch?v=BBdC1rl5sKY&pp=ygUa7Jyk7ZWYIOyCrOqxtOydmCDsp4Dtj4nshKA%3D")
                .build();
    }

    public DeleteDiaryRes deleteDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디 값의 일기기록이 없습니다."));

        diary.setStatus(STATUS.DEACTIVATE);

        return new DeleteDiaryRes(diary.getId());
    }

    public ModifyDiaryRes modifyDiary(Long diaryId, ModifyDiaryReq modifyDiaryReq) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(NotFoundDiaryException::new);

        diary.setDescription(modifyDiaryReq.getDescription());
        imageRepository.deleteImageByDiary(diary);
        List<String> imageUris = modifyDiaryReq.getImageUris();
        imageUris.stream()
                .map(uri -> new Image(uri, diary))
                .forEach(image -> imageRepository.save(image));
        List<String> uris = diary.getImages().stream()
                .map(image -> image.getUri()).toList();

        return new ModifyDiaryRes(diary.getId());
    }

    private List<String> initEmotionList(int capacity) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            newList.add("");
        }
        return newList;
    }

    private LocalDateTime periodToLocalDateTime(String period) {
        final String FIRST_DATE = "01";
        LocalDate localDate = periodToLocalDate(period + FIRST_DATE);
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    private LocalDate periodToLocalDate(String period) {
        return LocalDate.of(2000 + Integer.parseInt(period.substring(0, 2)),
                Integer.parseInt(period.substring(2, 4)),
                Integer.parseInt(period.substring(4, 6)));
    }

}
