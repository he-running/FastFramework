package com.he.data.manager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by hesh on 2018/2/9.
 */
@Singleton
public class BluetoothManager {

    private static final String TAG = "BluetoothManager";

    private Context context;

    private BluetoothAdapter mBtAdapter;

    @Inject
    public BluetoothManager(Context context){
        this.context=context;
        initBluetooth();
        registerReceiver();
    }

    private void initBluetooth(){
        mBtAdapter =BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * 注册广播接收器,接收蓝牙广播
     */
    private void registerReceiver(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
    }

    public BluetoothAdapter getBtAdapter(){
        return mBtAdapter;
    }


    public int getState(){
       return mBtAdapter.getState();
    }

    public boolean isEnabled(){
       return mBtAdapter.isEnabled();
    }

    public boolean openBt(){
       return mBtAdapter.enable();
    }

    public boolean closeBt(){
       return mBtAdapter.disable();
    }

    public boolean startDiscovery(){
       return mBtAdapter.startDiscovery();
    }

    public boolean cancelDiscovery(){
        return mBtAdapter.cancelDiscovery();
    }

    // 广播接收发现蓝牙设备
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device!=null)
                    Log.i(TAG, "蓝牙设备+地址："+device.getName()
                            +"--"
                            +device.getAddress());
            }
        }
    };

}
