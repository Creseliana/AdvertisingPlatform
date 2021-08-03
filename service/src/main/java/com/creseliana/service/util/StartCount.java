package com.creseliana.service.util;

public final class StartCount {

    public static int count(int page, int amount) {
        return (page - 1) * amount;
    }
}
