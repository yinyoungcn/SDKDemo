package com.example.sdkserver.operation.proxy.impl;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.sdkcontract.ClientInterface;
import com.example.sdkcontract.bean.ResultDataBean;
import com.example.sdkserver.operation.proxy.AbsMapProxy;

import java.util.List;

public class AmapProxy extends AbsMapProxy {

    private final String TAG = AmapProxy.this.getClass().getSimpleName();


    private RemoteCallbackList<ClientInterface> mRemoteCallbackList;
    private int state;
    private List<ResultDataBean> resultList;

    public AmapProxy() {
        Log.d(TAG, "AmapProxy");
        mRemoteCallbackList = new RemoteCallbackList<>();
    }

    @Override
    public void registerClient(final ClientInterface cl) throws RemoteException {
        Log.d(TAG, "registerClient, currentThreadName::" + Thread.currentThread().getName());
        mRemoteCallbackList.register(cl);
    }

    @Override
    public void unRegisterClient(ClientInterface cl) throws RemoteException {
        Log.d(TAG, "unRegisterClient, currentThreadName::" + Thread.currentThread().getName());
        if (null != mRemoteCallbackList) {
            int size = mRemoteCallbackList.beginBroadcast();
            for (int i = 0; i < size; i++) {
                ClientInterface listener = mRemoteCallbackList.getBroadcastItem(i);
                mRemoteCallbackList.unregister(listener);
            }
            mRemoteCallbackList.finishBroadcast();
        }
    }

    @Override
    public void openMap() throws RemoteException {
        Log.d(TAG, "openMap, currentThreadName::" + Thread.currentThread().getName());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // TODO

            }});
    }

    @Override
    public void closeMap() throws RemoteException {
        Log.d(TAG, "closeMap, currentThreadName:" + Thread.currentThread().getName());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // TODO

            }});
    }

    @Override
    public int getMapState() throws RemoteException {
        return ++state;
    }

    @Override
    public List<ResultDataBean> getResult() throws RemoteException {
        resultList.add(new ResultDataBean(state, "data_" + state));
        return resultList;
    }


    @Override
    public void showMapState(int state) throws RemoteException {
        Log.d(TAG, "showMapState, state:" + state + ", currentThreadName::" + Thread.currentThread().getName());
        if (this.state <= 0) {
            this.state = state;
        }
        if (null != mRemoteCallbackList) {
            int size = mRemoteCallbackList.beginBroadcast();
            for (int i = 0; i < size; i++) {
                ClientInterface listener = mRemoteCallbackList.getBroadcastItem(i);
                if (null != listener) {
                    try {
                        listener.showMapState(this.state);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            mRemoteCallbackList.finishBroadcast();
        }
    }

    @Override
    public void showResult(List<ResultDataBean> resultList) throws RemoteException {
        Log.d(TAG, "showResult, resultListSize:" + resultList.size() + ", currentThreadName::" + Thread.currentThread().getName());
        if (null == this.resultList) {
            this.resultList = resultList;
        }
        if (null != mRemoteCallbackList) {
            int size = mRemoteCallbackList.beginBroadcast();
            for (int i = 0; i < size; i++) {
                ClientInterface listener = mRemoteCallbackList.getBroadcastItem(i);
                if (null != listener) {
                    try {
                        listener.showResult(this.resultList);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            mRemoteCallbackList.finishBroadcast();
        }
    }





}
