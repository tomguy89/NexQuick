package com.balbadak.nexquickpro;

import android.os.Parcel;
import android.os.Parcelable;

public class ListViewItem implements Parcelable {
    private String titleStr;
    private String descStr;
    private String urgentStr;
    private int callNum;
    private int orderNum;
    private int quickType;

    protected ListViewItem() {
        super();
    }

    protected ListViewItem(Parcel in) {
        titleStr = in.readString();
        descStr = in.readString();
        urgentStr = in.readString();
        callNum = in.readInt();
        orderNum = in.readInt();
        quickType = in.readInt();
    }

    public static final Creator<ListViewItem> CREATOR = new Creator<ListViewItem>() {
        @Override
        public ListViewItem createFromParcel(Parcel in) {
            return new ListViewItem(in);
        }

        @Override
        public ListViewItem[] newArray(int size) {
            return new ListViewItem[size];
        }
    };

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDescStr() {
        return descStr;
    }

    public void setDescStr(String descStr) {
        this.descStr = descStr;
    }

    public int getCallNum() {
        return callNum;
    }

    public void setCallNum(int callNum) {
        this.callNum = callNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getQuickType() {
        return quickType;
    }

    public void setQuickType(int quickType) {
        this.quickType = quickType;
    }

    public String getUrgentStr() {
        return urgentStr;
    }

    public void setUrgentStr(String urgentStr) {
        this.urgentStr = urgentStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titleStr);
        parcel.writeString(descStr);
        parcel.writeString(urgentStr);
        parcel.writeInt(callNum);
        parcel.writeInt(orderNum);
        parcel.writeInt(quickType);
    }


    @Override
    public String toString() {
        return getTitleStr() +"/"+ getDescStr();
    }



}


