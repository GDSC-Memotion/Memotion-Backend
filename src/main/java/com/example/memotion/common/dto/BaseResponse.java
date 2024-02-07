package com.example.memotion.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.example.memotion.common.dto.ResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"code", "status", "message", "timestamp", "result"})
public class BaseResponse<T> {

    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public BaseResponse(T result) {
        this.code = SUCCESS.getCode();
        this.status = SUCCESS.getStatus();
        this.message = SUCCESS.getMessage();
        this.timestamp = LocalDateTime.now();
        this.result = result;
    }
    public BaseResponse(ResponseStatus status) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.result = null;
        this.timestamp = LocalDateTime.now();
    }

    public BaseResponse(ResponseStatus status, String message) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = message;
        this.result = null;
        this.timestamp = LocalDateTime.now();
    }
}
