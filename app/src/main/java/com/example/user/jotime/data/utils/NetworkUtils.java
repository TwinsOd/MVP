package com.example.user.jotime.data.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtils {
    private NetworkUtils() {
        //empty
    }

    public static boolean checkWifiOnAndConnected(Context applicationContext) {
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifiMgr = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            return wifiInfo.getNetworkId() != -1;
        } else {
            return false; // Wi-Fi adapter is OFF
        }
    }
}
