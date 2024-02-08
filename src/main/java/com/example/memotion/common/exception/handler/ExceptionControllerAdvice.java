package com.example.memotion.common.exception.handler;

import com.example.memotion.common.dto.BaseResponse;
import com.example.memotion.common.dto.ResponseStatus;
import com.example.memotion.common.exception.NotFoundDiaryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundDiaryException.class)
    public ResponseEntity<BaseResponse> notFoundDiaryHandle(NotFoundDiaryException e){
        log.error(e.getMessage());
        return ResponseEntity.status(ResponseStatus.NOT_FOUND_DIARY.getStatus())
                .body(new BaseResponse(ResponseStatus.NOT_FOUND_DIARY, "정의되지 않은 서버 에러입니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> baseErrorResponse(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(ResponseStatus.NOT_DEFINED_ERROR.getStatus())
                .body(new BaseResponse(ResponseStatus.NOT_DEFINED_ERROR, "정의되지 않은 서버 에러입니다."));
    }
}
