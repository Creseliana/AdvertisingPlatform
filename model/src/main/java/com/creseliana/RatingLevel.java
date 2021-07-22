package com.creseliana;

import java.util.Arrays;

public enum RatingLevel {
    EXCELLENT(5),
    GOOD(4),
    AVERAGE(3),
    BELOW_AVERAGE(2),
    POOR(1);

    private final int rate;

    RatingLevel(int rate) {
        this.rate = rate;
    }

    public static RatingLevel getRatingLevel(int rate) {
        return Arrays.stream(RatingLevel.values())
                .filter(level -> level.rate == rate)
                .findFirst()
                .orElseThrow(); //todo throw
    }

    public int getRate() {
        return rate;
    }
}
