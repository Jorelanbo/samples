package com.ktw.ktwlib.util;

// Created by JS on 2019/5/28.

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * 获取当前日期字符串
     *
     * @return 日期字符串
     */
    public static String getCurrentDate() {
        Date currentDate = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(currentDate);
    }

    /**
     * 获取一个月前的日期字符串
     *
     * @return 时间字符串
     */
    public static String getOneMonthBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date oneMonthBeforeDate = calendar.getTime();
        return simpleDateFormat.format(oneMonthBeforeDate);
    }

    /**
     * 获取一个月前的日期时间戳
     *
     * @return 时间戳
     */
    public static long getOneMonthBeforeDateSeconds() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        return calendar.getTime().getTime();
    }

    /**
     * 获取一个月前的时间字符串
     *
     * @return 时间字符串
     */
    public static String getOneMonthBeforeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date oneMonthBeforeDate = calendar.getTime();
        return simpleTimeFormat.format(oneMonthBeforeDate);
    }

    /**
     * 获取下一天的时间字符串
     *
     * @param date 时间对象
     * @return 下一天的日期字符串
     */
    public static String getOneDayLaterDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date oneDayLaterDate = calendar.getTime();
        return simpleDateFormat.format(oneDayLaterDate);
    }

    /**
     * 获取下一天的时间字符串
     *
     * @return 下一天的时间字符串
     */
    public static String getOneDayLaterTimeString() {
        Date currentTime = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date oneDayLaterDate = calendar.getTime();
        return simpleTimeFormat.format(oneDayLaterDate);
    }

    /**
     * 获取当前时间
     *
     * @return 时间字符串
     */
    public static String getCurrentTime() {
        Date currentTime = new Date(System.currentTimeMillis());
        return simpleTimeFormat.format(currentTime);
    }

    /**
     * 根据时间对象获取日期字符串
     *
     * @param date 时间对象
     * @return 日期字符串
     */
    public static String getDateString(Date date) {
        return simpleDateFormat.format(date);
    }

    /**
     * 根据时间戳获取日期字符串
     *
     * @param dateTimeMillis 时间戳
     * @return 日期字符串
     */
    public static String getDateString(Long dateTimeMillis) {
        Date date = new Date(dateTimeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据时间对象获取时间字符串
     *
     * @param date 时间对象
     * @return 时间字符串
     */
    public static String getTimeString(Date date) {
        return simpleTimeFormat.format(date);
    }

    /**
     * 根据时间戳获取时间字符串
     *
     * @param TimeTimeMillis 时间戳
     * @return 时间字符串
     */
    public static String getTimeString(Long TimeTimeMillis) {
        Date date = new Date(TimeTimeMillis);
        return simpleTimeFormat.format(date);
    }
}
