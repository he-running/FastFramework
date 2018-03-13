package com.he.data.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by hesh on 2018/2/9.
 */
@Singleton
public class WifiManagerUtil {

    private static final String TAG = "WifiManagerUtil";

    Context context;

    private WifiManager mWifiManager;

    @Inject
    public WifiManagerUtil(Context context){
        this.context=context;
        initWifiManger();
        registerReceiver();
    }

    private void initWifiManger(){
        mWifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * wifi是否已打开
     * @return
     */
    public boolean isWifiEnabled(){
        return mWifiManager.isWifiEnabled();
    }

    /**
     * 开始扫描
     */
    public void startScan(){
        mWifiManager.startScan();
    }

    /**
     * 打开WiFi
     */
    public void openWifi(){
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭WiFi
     */
    public void closeWifi(){
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 注册接收器
     */
    private void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, filter);
    }

    /**
     * 接收广播
     */
    private BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            List<ScanResult> mWifiScanResultLists;
            if (intent.getAction().equals(
                    WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                mWifiScanResultLists = mWifiManager.getScanResults();
                if (mWifiScanResultLists != null) {
                    for (ScanResult scanResult : mWifiScanResultLists) {
                        Log.i(TAG, " wifi名称+地址: "
                                + scanResult.SSID
                                +"--"
                                +scanResult.BSSID);
                    }
                }
            }
        }
    };
}
