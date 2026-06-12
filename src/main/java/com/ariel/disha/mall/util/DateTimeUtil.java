package com.ariel.disha.mall.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ariel
 * @apiNote DateTimeUtil
 * @serial
 */
public final class DateTimeUtil {

    private static final DateTimeFormatter FULL_DAY_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FULL_DAY_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd-hh-mm-ss");

    public static String formatDate(DateTime dateTime) {
        return dateTime.toString(FULL_DAY_FORMATTER);
    }

    public static String formatDateTime(DateTime dateTime) {
        return dateTime.toString(FULL_DAY_TIME_FORMATTER);
    }

    public static String currDateStr() {
        return formatDate(DateTime.now());
    }

    public static String currDateStr(Long time) {
        return formatDate(new DateTime(time));
    }

    public static String currDateTimeStr() {
        return formatDateTime(DateTime.now());
    }

    /**
     * 获取最近的天数数组
     * @param days 几天
     * @return 数组
     */
    public static List<String> getRecentDays(Integer days) {
        DateTime time = new DateTime();
        List<String> list = new LinkedList<>();
        for (int i = 0; i < days; i++) {
            list.add(0, time.toString(FULL_DAY_FORMATTER));
            time = time.minusDays(1);
        }
        return list;
    }

    public static Long getDayStartTime(Integer days) {
        return DateTime.now().withTime(0, 0, 0, 0).minusDays(days - 1).getMillis();
    }

    public static String getDayStr(Integer days) {
        return DateTime.now()
                .withTime(0, 0, 0, 0)
                .minusDays(days - 1)
                .toString(FULL_DAY_FORMATTER);
    }

    public static void main(String[] args) {
        System.out.println(getRecentDays(7));
    }
}
