package com.yoozlab.yshop.test;

import java.util.Random;
import java.util.stream.Collectors;

public class TestHelper {

    private static final Random random = new Random();

    public static String getRandomId(String prefix) {
        return prefix + getRandomId(prefix, 8);
    }

    public static String getRandomId(String prefix, long length) {
        return random.ints(0, 10)
                .limit(length)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    public static String getRandomText(int length) {
        var characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
