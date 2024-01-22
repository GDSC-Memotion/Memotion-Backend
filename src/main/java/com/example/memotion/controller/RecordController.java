package com.example.memotion.controller;

import com.example.memotion.common.BaseResponse;
import com.example.memotion.dto.CreateRecordReq;
import com.example.memotion.dto.CreateRecordRes;
import com.example.memotion.dto.FindDailyRecordRes;
import com.example.memotion.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    public BaseResponse<CreateRecordRes> createRecord(@RequestBody CreateRecordReq createRecordReq){
        return new BaseResponse(new CreateRecordRes(recordService.addRecord(createRecordReq)));
    }

    @GetMapping
    public BaseResponse<List<FindDailyRecordRes>> getDailyRecord(@RequestParam("period") String period) {
        List<FindDailyRecordRes> dailyRecord = recordService.findDailyRecord(period);
        return new BaseResponse<>(dailyRecord);
    }
}
