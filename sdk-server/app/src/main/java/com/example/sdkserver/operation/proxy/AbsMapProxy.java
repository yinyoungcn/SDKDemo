package com.example.sdkserver.operation.proxy;


import android.os.Handler;
import android.os.Looper;

import com.example.sdkcontract.ServerInterface;

public abstract class AbsMapProxy extends ServerInterface.Stub {

    protected Handler mHandler = new Handler(Looper.getMainLooper());

}
