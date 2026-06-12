package com.ariel.disha.mall.util;

import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.config.exception.AppException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ariel
 * @apiNote CastUtil
 * @serial
 */
public final class CastUtil {
    private CastUtil() {}

    public static <T> T cast(Object o, Class<T> tClass) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            throw new AppException("Cast " + tClass + " failed.");
        }
    }

    public static <T> T cast(String s, Class<T> tClass) {
        if (s == null || tClass == null) {
            return null;
        }
        if (tClass == String.class) {
            return (T) s;
        }
        if (tClass == Integer.class) {
            return (T) Integer.valueOf(s);
        }
        if (tClass == Long.class) {
            return (T) Long.valueOf(s);
        }
        if (tClass == Boolean.class) {
            return (T) Boolean.valueOf(s);
        }
        return JSONUtil.toBean(s, tClass);
    }

    public static <T> List<T> cast(List<String> ss, Class<T> tClass) {
        List<T> result = new ArrayList<>(ss.size());
        ss.forEach(s -> result.add(cast(s, tClass)));
        return result;
    }

    public static String toLeadingZeroString(Integer i, Integer length) {
        if (i == null || i < 0 || length == null || length < 0 || length > 10) {
            throw new IllegalArgumentException("param i or length is null");
        }
        int pow = Double.valueOf(Math.pow(10, length)).intValue();
        if (i > pow) {
            throw new IllegalArgumentException("param i scope out of length");
        }
        return String.valueOf(pow + i).substring(1);
    }

    public static void main(String[] args) {
        String leadingZeroString = toLeadingZeroString(7, 1);
        System.out.println("leadingZeroString = " + leadingZeroString);
    }
}
