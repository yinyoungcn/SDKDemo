package com.example.sdkclient;

import android.app.Application;
import android.os.RemoteException;
import android.util.Log;

import com.example.sdkcontract.OnSdkReadyCallback;
import com.example.sdkcontract.SdkContract;

public class SdkClientApplication extends Application {

    private static final String TAG = SdkClientApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        SdkContract.init(this, new OnSdkReadyCallback() {
            @Override
            public void onReady() {
                Log.d(TAG, "onReady");
                try {
                    SdkContract.getServer().registerClient(new ClientImpl());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRebooted() {
                Log.d(TAG, "onRebooted");
            }
        });
    }


}
