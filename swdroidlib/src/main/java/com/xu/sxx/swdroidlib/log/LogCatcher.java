package com.xu.sxx.swdroidlib.log;

import android.os.Environment;

import com.xu.sxx.swdroidlib.time.TimeUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Fun:打印日志
 * Created by sxx.xu on 7/7/2017.
 */

public class LogCatcher extends Thread {
    public static final String TAG = "LogCatcher";
    public static final String LOG_FILE_PATH = "/log-" + TimeUtil.transTimeMillis2DateStr(
            System.currentTimeMillis()) + ".txt";
    public static final String LOG_ROOT_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath();

    public static boolean open = true;
    private static LogCatcher instance = null;
    private static Process mLogcatProc = null;

    private BufferedReader mReader = null;
    private String packageName = "*";

    public static void startCatchLog(String packageName) {
        if (!open) return;
        if (instance == null) {
            instance = new LogCatcher();
            instance.packageName = packageName;
            instance.start();
        }
    }

    public static void stopCatchLog() {
        if (!open) return;
        if (mLogcatProc != null) {
            mLogcatProc.destroy();
            mLogcatProc = null;
        }
    }

    public static void logSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        android.util.Log.w("system",
                "New Start $$$$$$$$$$$$$$###########   " + time + "############$$$$$$$$$$$$$$$");
        android.util.Log.w("system", "android.os.Build.BOARD:" + android.os.Build.BOARD);
        android.util.Log.w("system", "android.os.Build.DEVICE:" + android.os.Build.DEVICE);
        android.util.Log.w("system", "android.os.Build.MANUFACTURER:"
                + android.os.Build.MANUFACTURER);
        android.util.Log.w("system", "android.os.Build.MODEL:" + android.os.Build.MODEL);
        android.util.Log.w("system", "android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
        android.util.Log.w("system", "android.os.Build.VERSION.CODENAME:"
                + android.os.Build.VERSION.CODENAME);
        android.util.Log.w("system", "android.os.Build.VERSION.RELEASE:"
                + android.os.Build.VERSION.RELEASE);
    }

    /**
     * 文件是否超过10M
     */
    public static boolean isFileSizeOutof10M(File file) throws Exception {
        if (file == null) return false;
        return file.length() >= 10485760;
    }

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    @Override
    public void run() {
        Log.i(TAG, "log catcher is running ...");
        BufferedWriter bufferedWriter = null;

        try {
            mLogcatProc = Runtime.getRuntime().exec("logcat" + packageName + ":I");
            mReader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));

            logSystemInfo();

            String line;
            File file = new File(LOG_ROOT_PATH + LOG_FILE_PATH);
            if (file.exists() && isFileSizeOutof10M(file)) {
                file.delete();
            }
            if (file.exists()) {
                System.out.println("log file size is:" + formatFileSize(file.length()));
            }

            FileWriter fw = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fw);
            while ((line = mReader.readLine()) != null) {
                bufferedWriter.append(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "Log catcher and bufferwriter has closed. ------------------");
            try {
                if (mReader != null) {
                    mReader.close();
                    mReader = null;
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
