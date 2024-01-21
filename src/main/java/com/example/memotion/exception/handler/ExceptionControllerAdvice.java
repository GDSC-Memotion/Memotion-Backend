package com.example.memotion.exception.handler;

import com.example.memotion.common.BaseResponse;
import com.example.memotion.common.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public BaseResponse baseErrorResponse(){
        return new BaseResponse(ResponseStatus.SERVER_ERROR);
    }
}
