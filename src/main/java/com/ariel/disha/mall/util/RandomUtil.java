package com.ariel.disha.mall.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author ariel
 * @apiNote RandomUtil
 * @serial
 */
public final class RandomUtil {
    private RandomUtil() {}

    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    public static String randomString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(chars[random.nextInt(chars.length)]);
        }
        return builder.toString();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(random.nextInt(10));
        }
    }
}
