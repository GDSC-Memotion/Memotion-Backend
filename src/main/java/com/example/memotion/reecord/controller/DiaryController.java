package com.example.memotion.reecord.controller;

import com.example.memotion.common.dto.BaseResponse;
import com.example.memotion.reecord.dto.CreateDiaryReq;
import com.example.memotion.reecord.dto.CreateDiaryRes;
import com.example.memotion.reecord.dto.DeleteDiaryRes;
import com.example.memotion.reecord.dto.FindCalendarDiaryRes;
import com.example.memotion.reecord.dto.FindDailyDiaryRes;
import com.example.memotion.reecord.dto.FindDiaryDetailRes;
import com.example.memotion.reecord.dto.ModifyDiaryReq;
import com.example.memotion.reecord.dto.ModifyDiaryRes;
import com.example.memotion.reecord.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public BaseResponse<CreateDiaryRes> createDiary(@RequestPart("info") CreateDiaryReq createDiaryReq,
                                                    @RequestPart("images") List<MultipartFile> images) throws IOException {
        return new BaseResponse(diaryService.addDiary(createDiaryReq, images));
    }

    @GetMapping
    public BaseResponse<List<FindDailyDiaryRes>> getDailyDiary(@RequestParam("period") String period) {
        List<FindDailyDiaryRes> dailyDiary = diaryService.findDailyDiary(period);
        return new BaseResponse<>(dailyDiary);
    }

    @DeleteMapping("/{diaryId}")
    public BaseResponse<DeleteDiaryRes> deleteDiary(@PathVariable("diaryId") Long diaryId) {
        return new BaseResponse<>(diaryService.deleteDiary(diaryId));
    }

    @GetMapping("/calendar")
    public BaseResponse<FindCalendarDiaryRes> getCalendarDiary(@RequestParam("period") String period) {
        return new BaseResponse(diaryService.findCalendarDiary(period));
    }

    @GetMapping("/{diaryId}")
    public BaseResponse<FindDiaryDetailRes> getDiaryDetail(@PathVariable("diaryId") Long diaryId) {
        return new BaseResponse(diaryService.findDetailDiary(diaryId));
    }

    @PutMapping("/{diaryId}")
    public BaseResponse<ModifyDiaryRes> modifyDiary(@PathVariable("diaryId") Long diaryId,
                                                    @RequestPart("info") ModifyDiaryReq modifyDiaryReq,
                                                    @RequestPart("images") List<MultipartFile> images) {
        return new BaseResponse<>(diaryService.modifyDiary(diaryId,modifyDiaryReq, images));
    }
}
