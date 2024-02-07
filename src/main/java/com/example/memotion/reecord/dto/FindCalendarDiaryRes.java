package com.example.memotion.reecord.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindCalendarDiaryRes {

    private List<String> emotions;

    public FindCalendarDiaryRes(List<String> emotions) {
        this.emotions = emotions;
    }
}
