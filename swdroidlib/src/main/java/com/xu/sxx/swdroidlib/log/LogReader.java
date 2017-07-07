package com.xu.sxx.swdroidlib.log;

import com.xu.sxx.swdroidlib.time.TimeUtil;

/**
 * Fun:打印日志
 * Created by sxx.xu on 7/7/2017.
 */

public class LogReader extends Thread {
    public static final String TAG = "LogReader";
    public static final String LOG_FILE_PATH = "/log-" + TimeUtil.transTimeMillis2DateStr(
            System.currentTimeMillis()) + ".txt";
    public static final String LOG_ROOT_PATH = "/sdcard";
}
