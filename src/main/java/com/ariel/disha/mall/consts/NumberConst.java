package com.ariel.disha.mall.consts;

/**
 * @author ariel
 * @apiNote NumberConst
 * @serial
 */
public class NumberConst {

    public static final int INT_1_NEG = -1;
    public static final int INT_0 = 0;
    public static final int INT_1 = 1;
    public static final int INT_2 = 2;
    public static final int INT_3 = 3;
    public static final int INT_4 = 4;
    public static final int INT_10 = 10;
    public static final int INT_100 = 100;
    public static final int INT_128 = 128;
    public static final int INT_10000 = 10000;

    public static final long LONG_0 = 0;
    public static final long LONG_1 = 1;
    public static final long LONG_1_NEG = -1;
    public static final long LONG_2 = 2;
    public static final long LONG_9 = 9;
    public static final long LONG_10 = 10;
    public static final long LONG_100 = 100;

    public static final double DOUBLE_1 = 1;
    public static final double DOUBLE_8R = 1d / 128;

    public static boolean gt0(int updated) {
        return updated > INT_0;
    }
}
