package com.example.sdkserver;

import android.app.Application;
import android.util.Log;

import com.example.sdkserver.operation.factory.impl.MapProxyFactory;

public class SdkServerApplication extends Application {

    private static final String TAG = SdkServerApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        MapProxyFactory.getInstance().createMapProxy();

    }
}
