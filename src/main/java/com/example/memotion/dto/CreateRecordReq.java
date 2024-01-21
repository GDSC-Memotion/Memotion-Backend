package com.example.memotion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRecordReq {
    private String description;
    private String mood;
    private List<String> imageUris;
}
