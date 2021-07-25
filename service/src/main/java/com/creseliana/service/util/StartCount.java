package com.creseliana.service.util;

public final class StartCount {

    public static int count(int page, int amount) {
        int start = (page - 1) * amount;

        if (start < 0) {
            throw new RuntimeException(); //todo handle
        }

        return start;
    }
}
