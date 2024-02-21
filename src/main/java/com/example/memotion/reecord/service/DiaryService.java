package com.example.memotion.reecord.service;

import com.example.memotion.common.domain.STATUS;
import com.example.memotion.common.exception.NotFoundDiaryException;
import com.example.memotion.common.exception.NotFoundMemberException;
import com.example.memotion.gcp.service.CloudStorageService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final CloudStorageService cloudStorageService;

    public CreateDiaryRes addDiary(CreateDiaryReq createDiaryReq, List<MultipartFile> images) throws IOException {
        Member member = memberRepository.findById(1L)
                .orElseThrow(NotFoundMemberException::new);

        LocalDateTime createdAt = stringTime2LocalDateTime(createDiaryReq.getTime());
        Diary diary = Diary.builder()
                .description(createDiaryReq.getDescription())
                .member(member)
                .createdAt(createdAt)
                .build();

        if (images == null) {
            Diary savedDiary = diaryRepository.save(diary);
            return new CreateDiaryRes(savedDiary.getId(), "anger");
        }

        List<String> imageUris = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageUri = cloudStorageService.uploadMultipartFileToCloudStorage(image);
//            String imageUri = saveFileToLocalServer(image);
            imageUris.add(imageUri);
        }
        Diary savedDiary = diaryRepository.save(diary);

        imageUris.stream()
                .map(uri -> new Image(uri, savedDiary))
                .forEach(image -> imageRepository.save(image));

        //TODO : 파이썬 서버와의 통신 및 분석 결과 저장 후 반환해야 함
        return new CreateDiaryRes(savedDiary.getId(), "anger");
    }

    private String saveFileToLocalServer(MultipartFile image) {
        String uniqueFileName = generateUniqueFileName(image.getOriginalFilename());
        Path filePath = Paths.get("temp" + File.separator + uniqueFileName);
        try {
            Files.copy(image.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }

    private String generateUniqueFileName(String originalFileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + timestamp + "_" + originalFileName;
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
        return LocalDateTime.of(dates.get(0), dates.get(1), dates.get(2), times.get(0), times.get(1), times.get(2));
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
            result.add(FindDailyDiaryRes.of(diary, imageUris, "anger"));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public FindCalendarDiaryRes findCalendarDiary(String period) {
        final int dateCapacity = 32;

        LocalDateTime localDateTime = periodToLocalDateTime(period);
        List<DailyEmotionAvgDTO> dailyEmotionAvgs = diaryRepository.findDiaryByCalendar(localDateTime, localDateTime.plusMonths(1));
        log.info(dailyEmotionAvgs.toString());
        List<String> emotions = initEmotionList(dateCapacity);
        log.info("size: " + emotions.size());

        emotions.add(0, "");

//        Map<Integer, String> emotions = new HashMap<>();
        log.info("size: " + emotions.size());

        for (DailyEmotionAvgDTO dailyEmotionAvgDTO : dailyEmotionAvgs) {
            String day = dailyEmotionAvgDTO.getCreateDate().split("-")[2];
            String maxEmotionName = dailyEmotionAvgDTO.getMaxEmotionName();
            emotions.add(Integer.parseInt(day), maxEmotionName);
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
                .build();

        // TODO : 유튜브 API 수정 필요
        return FindDiaryDetailRes.builder()
                .diaryId(diary.getId())
                .description(diary.getDescription())
                .imageUris(imageUris)
                .analysisResult(diaryAnalysisResultDTO)
                .youtubeUri("https://www.youtube.com/watch?v=BBdC1rl5sKY&pp=ygUa7Jyk7ZWYIOyCrOqxtOydmCDsp4Dtj4nshKA%3D")
                .youtubeMusicUri("https://music.youtube.com/watch?v=BBdC1rl5sKY&pp=ygUa7Jyk7ZWYIOyCrOqxtOydmCDsp4Dtj4nshKA%3D")
                .createdAt(diary.getCreatedAt())
                .emotion("anger")
                .build();
    }

    public DeleteDiaryRes deleteDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디 값의 일기기록이 없습니다."));

        diary.setStatus(STATUS.DEACTIVATE);

        return new DeleteDiaryRes(diary.getId());
    }

    public ModifyDiaryRes modifyDiary(Long diaryId, ModifyDiaryReq modifyDiaryReq, List<MultipartFile> images) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(NotFoundDiaryException::new);

        //기존 이미지 경로 찾기
        List<Image> presentImages = diary.getImages();

        //기존 경로에 있는 이미지 삭제
        for (Image image : presentImages) {
            //UUID가 포함된 파일이름을 디코딩해줍니다.
            File file = new File(image.getUri());
            boolean result = file.delete();
            log.info("DELETE IMAGE: " + file.getAbsolutePath());
            log.info("RESULT : " + result);
        }

        //DB 기록 삭제
        imageRepository.deleteImageByDiary(diary);

        List<String> imageUris = new ArrayList<>();

        //새 사진 로컬 저장
        for (MultipartFile image : images) {
//            String imageUri = cloudStorageService.uploadMultipartFileToCloudStorage(image);
            String imageUri = saveFileToLocalServer(image);
            imageUris.add(imageUri);
        }

        //새 사진 경로 DB 추가
        imageUris.stream()
                .map(uri -> new Image(uri, diary))
                .forEach(image -> imageRepository.save(image));
        diary.setDescription(modifyDiaryReq.getDescription());

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
