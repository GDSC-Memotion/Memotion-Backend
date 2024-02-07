package com.example.memotion.common.exception.handler;

import com.example.memotion.common.dto.BaseResponse;
import com.example.memotion.common.dto.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public BaseResponse baseErrorResponse(Exception e){
        log.error(e.getMessage());
        return new BaseResponse(ResponseStatus.SERVER_ERROR);
    }
}
