package com.example.sdkcontract;

import com.example.sdkcontract.bean.ResultDataBean;


interface ClientInterface {

    void showMapState(int state);

    void showResult(in List<ResultDataBean> resultList);


}
