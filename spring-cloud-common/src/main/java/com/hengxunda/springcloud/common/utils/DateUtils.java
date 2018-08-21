package com.hengxunda.springcloud.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String yyyymmdd = "yyyy-MM-dd";
    public static final String hhmmss = "HH:mm:ss";
    public static final String yyyymmddhhmmss = yyyymmdd + " " + hhmmss;

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
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(yyyymmddhhmmss));
    }

    public static String getDateTime(String pattern) {
        return format(pattern);
    }

    public static String getDateTime() {
        return format(yyyymmddhhmmss);
    }

    public static String getDate() {
        return format(yyyymmdd);
    }

    public static String getTime() {
        return format(hhmmss);
    }

    private static String format(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(formatter);
    }

    public static boolean isLeapYear(LocalDate localDate) {
        if (localDate.isLeapYear()) {
            return true;
        }
        return false;
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

        LocalDate localDate = getLocalDate().parse("2018-08-08").plusDays(5);
        System.out.println("args = " + localDate);

        System.out.println("args = " + localDate.isAfter(getLocalDate()));
    }
}
