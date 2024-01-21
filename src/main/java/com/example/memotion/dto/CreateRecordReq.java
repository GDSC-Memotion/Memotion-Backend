package com.example.memotion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRecordReq {
    private String description;
    private String mood;
    private String imageUri;
}
