package com.xu.sxx.swdroidlib.time;

/**
 * Fun:时间刺客
 * Created by sxx.xu on 7/7/2017.
 */

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

    public static final int ONE_DAY_SECONDS = 60 * 60 * 24;
    private static final String TAG = "TimeUtil";

    /**
     * 毫秒 ==>> 2016-05-26
     */
    public static String transTimeMillis2Date(long millis) {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(millis));
        date = simpleDateFormat.format(calendar.getTime());
        return date.toString();
    }


    /**
     * 毫秒 ==>> 2017-07-07 16:54
     */
    public static String transTimeMillis2DateStr(long millis) {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(millis));
        date = simpleDateFormat.format(calendar.getTime());
        return date.toString();
    }

    /**
     * 毫秒 ==>> 2017-07-07 16:54:47
     */
    public static String transTimeMillis2DateH(long millis) {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(millis));
        date = simpleDateFormat.format(calendar.getTime());
        return date.toString();
    }

    /**
     * 2016-07-01 12:00 ==>> 毫秒
     */
    public static long transTimeDate2Millis(String s) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return simpleDateFormat.parse(s).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 2016-07-01 12:00:00 ==>> 毫秒
     */
    public static long transTime2Millis(String s) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return simpleDateFormat.parse(s).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 2016-07-01  ==>> 毫秒
     */
    public static long transTimeDateToMillis(String s) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(s).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 获取几天后的日期
     *
     * @param day 天数。比如，获取3天后的日期，传入3
     */
    public static String getAfterDays(@NonNull int day) {
        if (day <= 0) {
            return transTimeMillis2DateStr(System.currentTimeMillis());
        }
        return transTimeMillis2DateStr(System.currentTimeMillis() + day * ONE_DAY_SECONDS);
    }

    /**
     * 两个毫秒值时间是否是同一天
     */
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        return transTimeMillis2DateStr(ms1).equals(transTimeMillis2DateStr(ms2));
    }
}

