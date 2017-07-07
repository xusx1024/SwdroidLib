package com.xu.sxx.swdroidlib.device;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Fun: 手机相关信息
 * Created by sxx.xu on 7/7/2017.
 */

public class PhoneInfo {

    private static final String TAG = PhoneInfo.class.getSimpleName();
    private static final String ETHERNET_MAC_ADDRESS = "/sys/class/net/eth0/address";

    public static String getWifiMacAddress(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        return mac;
    }













}
