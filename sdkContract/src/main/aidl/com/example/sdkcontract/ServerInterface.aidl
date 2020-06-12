package com.example.sdkcontract;

import com.example.sdkcontract.ClientInterface;
import com.example.sdkcontract.bean.ResultDataBean;


interface ServerInterface {

    void registerClient(ClientInterface cl);

    void unRegisterClient(ClientInterface cl);

    void openMap();

    void closeMap();

    /**
     * 客户端获取服务端 基本数据 类型的数据
     * @param resultList
     * @throws RemoteException
     */
    int getMapState();

    /**
     * 客户端获取服务端 序列化 类型的数据
     * @param resultList
     * @throws RemoteException
     */
    List<ResultDataBean> getResult();


    /**
     * 服务端向客户端传递 基本数据 类型的数据
     * @param resultList
     * @throws RemoteException
     */
    void showMapState(int state);

    /**
     * 服务端向客户端传递 序列化 类型的数据
     * @param resultList
     * @throws RemoteException
     */
    void showResult(in List<ResultDataBean> resultList);


}
