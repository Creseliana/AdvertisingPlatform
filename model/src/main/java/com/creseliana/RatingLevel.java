package com.creseliana;

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
}
