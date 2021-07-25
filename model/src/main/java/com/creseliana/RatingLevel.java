package com.creseliana;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Optional;

@Log4j2
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

    public static Optional<RatingLevel> getRatingLevel(int rate) {
        return Arrays.stream(RatingLevel.values())
                .filter(level -> level.rate == rate)
                .findFirst();
    }

    public int getRate() {
        return rate;
    }
}
