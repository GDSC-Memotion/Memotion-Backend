package com.example.memotion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindCalendarRecordRes {

    private List<String> emotions;

    public FindCalendarRecordRes(List<String> emotions) {
        this.emotions = emotions;
    }
}
