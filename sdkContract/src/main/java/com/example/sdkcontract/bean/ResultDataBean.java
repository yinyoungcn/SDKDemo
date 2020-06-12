package com.example.sdkcontract.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultDataBean implements Parcelable {

    public int resultId;
    public String resultData;

    public ResultDataBean(int resultId, String resultData) {
        this.resultId = resultId;
        this.resultData = resultData;
    }

    protected ResultDataBean(Parcel in) {
        resultId = in.readInt();
        resultData = in.readString();
    }

    public static final Creator<ResultDataBean> CREATOR = new Creator<ResultDataBean>() {
        @Override
        public ResultDataBean createFromParcel(Parcel in) {
            return new ResultDataBean(in);
        }

        @Override
        public ResultDataBean[] newArray(int size) {
            return new ResultDataBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(resultId);
        parcel.writeString(resultData);
    }
}
