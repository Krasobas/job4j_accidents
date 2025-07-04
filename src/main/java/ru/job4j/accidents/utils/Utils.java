package ru.job4j.accidents.utils;

import java.util.UUID;

public class Utils {
    public static long generateUniqueId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
