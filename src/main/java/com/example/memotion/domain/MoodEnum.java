package com.example.memotion.domain;

public enum MoodEnum {
    HAPPY,
    UNHAPPY;

    public static MoodEnum from(String mood) {
        if (mood.equals("HAPPY")) {
            return MoodEnum.HAPPY;
        }
        if (mood.equals("UNHAPPY")) {
            return MoodEnum.UNHAPPY;
        }
        return MoodEnum.HAPPY;
    }
}
