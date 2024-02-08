package com.example.memotion.reecord.controller;

import com.example.memotion.common.dto.BaseResponse;
import com.example.memotion.reecord.dto.CreateDiaryReq;
import com.example.memotion.reecord.dto.CreateDiaryRes;
import com.example.memotion.reecord.dto.ModifyDiaryReq;
import com.example.memotion.reecord.dto.DeleteDiaryRes;
import com.example.memotion.reecord.dto.FindCalendarDiaryRes;
import com.example.memotion.reecord.dto.FindDailyDiaryRes;
import com.example.memotion.reecord.dto.FindDiaryDetailRes;
import com.example.memotion.reecord.dto.ModifyDiaryRes;
import com.example.memotion.reecord.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public BaseResponse<CreateDiaryRes> createDiary(@RequestBody CreateDiaryReq createDiaryReq){
        return new BaseResponse(diaryService.addDiary(createDiaryReq));
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
    public BaseResponse<ModifyDiaryRes> modifyDiary(@PathVariable("diaryId") Long diaryId, @RequestBody ModifyDiaryReq modifyDiaryReq) {
        return new BaseResponse<>(diaryService.modifyDiary(diaryId,modifyDiaryReq));
    }
}
