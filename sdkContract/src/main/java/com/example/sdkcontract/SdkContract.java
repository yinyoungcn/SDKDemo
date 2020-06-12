package com.example.sdkcontract;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import static android.content.Context.BIND_AUTO_CREATE;

public class SdkContract {

    private static final String TAG = SdkContract.class.getSimpleName();

    private Context mContext;

    private OnSdkReadyCallback mCallback;
    private BinderPoolServiceConnection mServiceConnection;
    private BinderPoolDeathRecipient mDeathRecipient;
    private BinderPool mBinderPool;
    private ServerInterface mServerInterface;


    SdkContract() {
        mServiceConnection = new BinderPoolServiceConnection();
        mDeathRecipient = new BinderPoolDeathRecipient();
    }

    public static void init(Context context) {
        Log.d(TAG, "init, context");
        getInstance().initSdk(context, null);
    }

    public static void init(Context context, OnSdkReadyCallback onSdkReadyCallback) {
        Log.d(TAG, "init, context, onSdkReadyCallback");
        getInstance().initSdk(context, onSdkReadyCallback);
    }

    private void initSdk(Context context, OnSdkReadyCallback callback) {
        if (!getInstance().onServiceConnection()) {
            mContext = context;
            if (null != callback) {
                mCallback = callback;
            }
            bindAidlService();
        }
    }

    public static ServerInterface getServer() {
        return getInstance().getServerInterface();
    }

    private ServerInterface getServerInterface() {
        return mServerInterface;
    }

    public static boolean onReady() {
        return getInstance().onServiceConnection();
    }

    private boolean onServiceConnection() {
        return (null != mBinderPool && mBinderPool.asBinder().isBinderAlive());
    }

    public static void release() {
        if (getInstance().onServiceConnection()) {
            getInstance().unbindAidlService();
            getInstance().cleanCallback();
        }
    }

    private void cleanCallback() {
        Log.d(TAG, "cleanCallback");
        mServerInterface = null;
        mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
        mBinderPool = null;
        mCallback = null;
    }

    private void bindAidlService() {
        Log.d(TAG, "bindAidlService");
        Intent intent = new Intent();
        intent.setPackage("com.example.sdkserver");
        intent.setAction("com.example.sdkserver.IBINDERPOOLSERVICE");
        mContext.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void unbindAidlService() {
        Log.d(TAG, "unbindAidlService");
        if (null != mBinderPool && mBinderPool.asBinder().isBinderAlive()) {
            mContext.unbindService(mServiceConnection);
        }
    }

    private class BinderPoolServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            mBinderPool = BinderPool.Stub.asInterface(iBinder);
            if (null != mBinderPool && mBinderPool.asBinder().isBinderAlive()) {
                try {
                    mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
                    mServerInterface = ServerInterface.Stub.asInterface(mBinderPool.acquireBinder());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                // 通知回调
                if (null != mCallback) {
                    mCallback.onReady();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            bindAidlService();
        }
    }

    private class BinderPoolDeathRecipient implements IBinder.DeathRecipient {
        @Override
        public void binderDied() {
            bindAidlService();
        }
    }

    private static class SingletonHolder {
        private static final SdkContract sInstance = new SdkContract();
    }

    private static SdkContract getInstance() {
        return SingletonHolder.sInstance;
    }

}
