package com.ariel.disha.mall.util;

/**
 * @author ariel
 * @apiNote AssertUtil
 * @serial
 */
public final class AssertUtil {

    public static <T> T notNull(T t) {
        if (t == null) {
            throw new AssertException("The argument is null, but expect val is not null");
        }
        return t;
    }

    public static <T extends Number> T gte0(T t) {
        notNull(t);
        if (t.intValue() < 0) {
            throw new AssertException("The number is less than 0, but expect val is great than 0");
        }
        return t;
    }

    public static <T extends Number> T gte(T t, T target) {
        notNull(t);
        notNull(target);
        if (t.intValue() < target.intValue()) {
            throw new AssertException("The number is less than target, but expect val is great than target");
        }
        return t;
    }

    public static class AssertException extends RuntimeException {
        public AssertException(String message) {
            super(message);
        }
    }
}
