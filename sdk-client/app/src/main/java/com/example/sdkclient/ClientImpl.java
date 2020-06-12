package com.example.sdkclient;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.example.sdkcontract.ClientInterface;
import com.example.sdkcontract.bean.ResultDataBean;

import java.util.List;

public class ClientImpl extends ClientInterface.Stub {

    private final String TAG = this.getClass().getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper());


    ClientImpl() {
        Log.d(TAG, "ClientImpl");
    }

    @Override
    public void showMapState(final int state) throws RemoteException {
        Log.d(TAG, "showMapState, state:" + state + ", currentThreadName:" + Thread.currentThread().getName());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // TODO

            }
        });
    }

    @Override
    public void showResult(final List<ResultDataBean> resultList) throws RemoteException {
        Log.d(TAG, "showResult, resultListSize:" + resultList.size() + ", currentThreadName:" + Thread.currentThread().getName());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // TODO
                for (ResultDataBean resultDataBean : resultList) {
                    Log.d(TAG, "showResult, run, resultId:" + resultDataBean.resultId + ", resultData:" + resultDataBean.resultData);
                }
            }
        });

    }

}
