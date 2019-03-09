package com.master.util;

import javafx.scene.Parent;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TimeUtil {
    public static final DateTimeFormatter LEAST_HOUR= DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter LEAST_MINUTE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter MD_C= DateTimeFormatter.ofPattern("M月d日");
    public static final DateTimeFormatter YMD_1= DateTimeFormatter.ofPattern("yyyy/M/d");
    public static List<DateTimeFormatter> formatterList;
    static {
        formatterList = new ArrayList<>();
        formatterList.add(LEAST_HOUR);
        formatterList.add(YMD);
        formatterList.add(MD_C);
        formatterList.add(LEAST_MINUTE);
        formatterList.add(YMD_1);
    }
    public static final long MINUTE_MIL = 1000 * 60;
    public static final long HOUR_MIL = MINUTE_MIL * 60;

    public static final long DAY_MIL = 1000 * 60 * 60 * 24;



    //stay 用
    public static LocalDate parseStringToLocalDate(DateTimeFormatter sdf, String time){
        return LocalDate.from(LocalDateTime.parse(time, sdf));
    }

    public static String formateLocalDateToString(DateTimeFormatter sdf, LocalDate localDate) {
        return localDate.format(sdf);
    }
    public static long formateLocalDateToLong(LocalDate localDate) {
        return LocalDateTime.of(localDate,LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    //clear用
    public static long parseDatetimeStrToLong(DateTimeFormatter sdf, String time){
        return LocalDateTime.from(LocalDateTime.parse(time, sdf)).atZone(ZoneId.systemDefault()).
                toInstant().toEpochMilli();
    }
    //search
    public static long aotoParseDatetimeStrToLong(String time){
        if(null == time)
            return 0;
        LocalDateTime parse = null;
        if(time.length() == 13){
            parse = LocalDateTime.parse(time, LEAST_HOUR);
        }else {
            parse = LocalDateTime.of(LocalDate.parse(time, YMD),LocalTime.MIN);
        }
        return  LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).
                toInstant().toEpochMilli();
    }

    //clear
    public static long parseDateStrToLong(DateTimeFormatter sdf, String time){
        LocalDate date = LocalDate.from(LocalDate.parse(time, sdf));
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);
        return LocalDateTime.from(dateTime).atZone(ZoneId.systemDefault()).
                toInstant().toEpochMilli();
    }

    public static int getHour(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dt = LocalDateTime.ofInstant(instant, zone);
        return dt.getHour();
    }
    public static String formateLongByFormatter(DateTimeFormatter sdf, long time){
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dt = LocalDateTime.ofInstant(instant, zone);
        String format = sdf.format(dt);
        return format;
    }



    public static long get7DaysBeforeClock() {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime plus = current.minus(3, ChronoUnit.WEEKS);
        LocalDateTime localDateTime = plus.withHour(0).withMinute(0).withSecond(0);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long getEndMonthClock() {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime l1 = LocalDateTime.of(current.getYear(), current.getMonth().plus(1), 1, 0
                , 0, 0);
        return l1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static long getBeforeMonthClock() {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime l1 = LocalDateTime.of(current.getYear(), current.getMonth(), 1, 0
                , 0, 0);
        return l1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static long getBeforeHourClock() {
        long l = System.currentTimeMillis();
        return l - l % 3600000L;
    }
    public static long getTodayStartClock() {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime l1 = LocalDateTime.of(current.getYear(), current.getMonth(), current.getDayOfMonth(), 0
                , 0, 0);
        return l1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static void main(String[] args) {
        aotoParseDatetimeStrToLong("2019-03-06 04");
    }

}
