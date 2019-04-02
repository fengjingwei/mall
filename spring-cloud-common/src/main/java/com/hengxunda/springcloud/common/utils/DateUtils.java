package com.hengxunda.springcloud.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS = YYYY_MM_DD + " " + HH_MM_SS;

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate getLocalDate() {
        return getLocalDateTime().toLocalDate();
    }

    public static LocalTime getLocalTime() {
        return getLocalDateTime().toLocalTime();
    }

    public static LocalDateTime parse(String localDateTime) {
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }

    public static String getDateTime(String pattern) {
        return format(pattern);
    }

    public static String getDateTime() {
        return format(YYYY_MM_DD_HH_MM_SS);
    }

    public static String getDate() {
        return format(YYYY_MM_DD);
    }

    public static String getTime() {
        return format(HH_MM_SS);
    }

    private static String format(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(formatter);
    }

    public static boolean isLeapYear(LocalDate localDate) {
        return localDate.isLeapYear();
    }

    public static Date now() {
        return new Date();
    }

    public static void main(String[] args) {
        System.out.println("args = " + getDate());
        System.out.println("args = " + getTime());
        System.out.println("args = " + getDateTime());
        System.out.println("args = " + getDateTime("yyyy.MM.dd HH:mm"));

        System.out.println("args = " + parse("2018-07-14 14:11:27"));
        System.out.println("args = " + isLeapYear(LocalDate.now()));
    }
}
