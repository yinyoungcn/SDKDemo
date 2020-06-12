package com.example.sdkclient;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.sdkcontract.OnSdkReadyCallback;
import com.example.sdkcontract.SdkContract;
import com.example.sdkcontract.ServerInterface;
import com.example.sdkcontract.bean.ResultDataBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_bind_aidl_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SdkContract.init(MainActivity.this, new OnSdkReadyCallback() {
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
        });

        findViewById(R.id.btn_main_release_aidl_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean onReady = SdkContract.onReady();
                Log.d(TAG, "btn_main_unbind_aidl_service, onReady:" + onReady);
                if (onReady) {
                    SdkContract.release();
                }
            }
        });

        findViewById(R.id.btn_main_open_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onReady = SdkContract.onReady();
                Log.d(TAG, "btn_main_open_map, onReady:" + onReady);
                if (onReady) {
                    ServerInterface server = SdkContract.getServer();
                    if (null != server) {
                        try {
                            server.openMap();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        findViewById(R.id.btn_main_close_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onReady = SdkContract.onReady();
                Log.d(TAG, "btn_main_close_map, onReady:" + onReady);
                if (onReady) {
                    ServerInterface server = SdkContract.getServer();
                    if (null != server) {
                        try {
                            server.closeMap();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        findViewById(R.id.btn_main_get_map_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean onReady = SdkContract.onReady();
                Log.d(TAG, "btn_main_get_map_state, onReady:" + onReady);
                if (onReady) {
                    ServerInterface server = SdkContract.getServer();
                    if (null != server) {
                        try {
                            int mapState = server.getMapState();
                            Log.d(TAG, "btn_main_get_map_state, mapState:" + mapState);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        findViewById(R.id.btn_main_get_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean onReady = SdkContract.onReady();
                Log.d(TAG, "btn_main_get_result, onReady:" + onReady);
                if (onReady) {
                    ServerInterface server = SdkContract.getServer();
                    if (null != server) {
                        try {
                            List<ResultDataBean> resultList = server.getResult();
                            Log.d(TAG, "btn_main_get_result, resultListSize:" + resultList.size());
                            for (ResultDataBean resultDataBean : resultList) {
                                Log.d(TAG, "btn_main_get_result, resultId:" + resultDataBean.resultId
                                        + ", resultData:" + resultDataBean.resultData);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }


}
