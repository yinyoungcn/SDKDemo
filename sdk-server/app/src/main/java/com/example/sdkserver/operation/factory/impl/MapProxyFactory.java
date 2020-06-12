package com.example.sdkserver.operation.factory.impl;

import android.util.Log;

import com.example.sdkserver.operation.factory.AbsMapProxyFactory;
import com.example.sdkserver.operation.proxy.AbsMapProxy;
import com.example.sdkserver.operation.proxy.impl.AmapProxy;

public class MapProxyFactory extends AbsMapProxyFactory {

    private static final String TAG = MapProxyFactory.class.getSimpleName();

    private AbsMapProxy mAbsMapProxy;

    MapProxyFactory() {
        Log.d(TAG, "MapProxyFactory, currentThreadName:" + Thread.currentThread().getName());
    }

    private static class SingletonHolder {
        private static final MapProxyFactory sInstance = new MapProxyFactory();
    }

    public static MapProxyFactory getInstance() {
        return MapProxyFactory.SingletonHolder.sInstance;
    }

    @Override
    public void createMapProxy() {
        Log.d(TAG, "createMapProxy, currentThreadName:" + Thread.currentThread().getName());
        if (null == mAbsMapProxy) {
            mAbsMapProxy = new AmapProxy();
        }
    }

    @Override
    public AbsMapProxy getMapProxy() {
        Log.d(TAG, "getMapProxy, currentThreadName:" + Thread.currentThread().getName());
        if (null == mAbsMapProxy) {
            createMapProxy();
        }
        return mAbsMapProxy;
    }


}
