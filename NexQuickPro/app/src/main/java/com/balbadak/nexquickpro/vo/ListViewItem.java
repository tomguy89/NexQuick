package com.balbadak.nexquickpro.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class ListViewItem implements Parcelable {
    private String titleStr;
    private String descStr;
    private int callNum;
    private int deliveryStatus;
    private String urgentStr;
    private int orderNum;
    private int quickType;
    private String senderPhone;
    private String receiverPhone;

    public ListViewItem() {
    }

    protected ListViewItem(Parcel in) {
        titleStr = in.readString();
        descStr = in.readString();
        callNum = in.readInt();
        deliveryStatus = in.readInt();
        urgentStr = in.readString();
        orderNum = in.readInt();
        quickType = in.readInt();
        senderPhone = in.readString();
        receiverPhone = in.readString();
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

    public String getUrgentStr() {
        return urgentStr;
    }

    public void setUrgentStr(String urgentStr) {
        this.urgentStr = urgentStr;
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

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

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

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titleStr);
        parcel.writeString(descStr);
        parcel.writeInt(callNum);
        parcel.writeInt(deliveryStatus);
        parcel.writeString(urgentStr);
        parcel.writeInt(orderNum);
        parcel.writeInt(quickType);
        parcel.writeString(senderPhone);
        parcel.writeString(receiverPhone);
    }

    @Override
    public String toString() {
        return getTitleStr() +"/"+ getDescStr();
    }
}


