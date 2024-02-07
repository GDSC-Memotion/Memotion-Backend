package com.example.memotion.reecord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiaryReq {
    private String description;
    private List<String> imageUris;
}
