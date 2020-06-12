package com.example.sdkserver;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.sdkcontract.bean.ResultDataBean;
import com.example.sdkserver.operation.factory.impl.MapProxyFactory;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_open_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "btn_main_open_map");
                try {
                    MapProxyFactory.getInstance().getMapProxy().openMap();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_main_close_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "btn_main_close_map");
                try {
                    MapProxyFactory.getInstance().getMapProxy().closeMap();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_main_show_map_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MapProxyFactory.getInstance().getMapProxy().showMapState(3);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_main_show_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<ResultDataBean> resultDataList = new ArrayList<>();
                    resultDataList.add(new ResultDataBean(1, "data_1"));
                    resultDataList.add(new ResultDataBean(2, "data_2"));
                    resultDataList.add(new ResultDataBean(3, "data_3"));
                    MapProxyFactory.getInstance().getMapProxy().showResult(resultDataList);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
