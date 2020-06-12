package com.example.sdkserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.sdkcontract.BinderPool;
import com.example.sdkserver.operation.factory.impl.MapProxyFactory;


public class BinderPoolService extends Service {

    private static final String TAG = BinderPoolService.class.getSimpleName();


    private class BinderPoolImpl extends BinderPool.Stub {
        BinderPoolImpl() {
            Log.d(TAG, "BinderPoolImpl");
        }
        @Override
        public IBinder acquireBinder() throws RemoteException {
            Log.d(TAG, "acquireBinder");
            return MapProxyFactory.getInstance().getMapProxy();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return new BinderPoolImpl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        try {
            MapProxyFactory.getInstance().getMapProxy().unRegisterClient(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
