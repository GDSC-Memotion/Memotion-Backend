package com.example.memotion.reecord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindCalendarDiaryRes {

    private Map<Integer, String> emotions;
}
