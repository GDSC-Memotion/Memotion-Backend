package com.example.memotion.controller;

import com.example.memotion.common.BaseResponse;
import com.example.memotion.dto.CreateRecordReq;
import com.example.memotion.dto.CreateRecordRes;
import com.example.memotion.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    public BaseResponse<CreateRecordRes> createRecord(@RequestBody CreateRecordReq createRecordReq){
        return new BaseResponse(new CreateRecordRes(recordService.addRecord(createRecordReq)));
    }
}
