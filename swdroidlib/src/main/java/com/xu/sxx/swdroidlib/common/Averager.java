package com.xu.sxx.swdroidlib.common;

import android.util.Log;

import java.util.ArrayList;

/**
 * Fun:平均数统计
 * Created by sxx.xu on 7/3/2017.
 */

public class Averager {
    private static final String TAG = "Averager";
    private ArrayList<Number> mNumbers = new ArrayList<>();

    /**
     * add
     */
    public synchronized void add(Number number) {
        mNumbers.add(number);
    }

    /**
     * clear
     */
    public void clear() {
        mNumbers.clear();
    }

    /**
     * size
     */
    public Number size() {
        return mNumbers.size();
    }

    /**
     * 获取平均数
     */
    public Number getAverage() {
        if (mNumbers.size() == 0) {
            return 0;
        } else {
            Float sum = 0f;
            for (int i = 0; i < mNumbers.size(); i++) {
                sum = sum.floatValue() + mNumbers.get(i).floatValue();
            }
            return sum / mNumbers.size();
        }
    }

    /**
     * 打印
     */
    public String print() {
        String str = "PrintList(" + size() + "):" + mNumbers;
        Log.i(TAG, str);
        return str;
    }
}
