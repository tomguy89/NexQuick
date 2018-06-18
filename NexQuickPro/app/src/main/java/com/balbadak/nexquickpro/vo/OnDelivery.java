package com.balbadak.nexquickpro.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class OnDelivery implements Parcelable {
    private int callNum;
    private int orderNum;
    private String callTime;
    private String senderName;
    private String senderPhone;
    private String senderAddress;
    private String senderAddressDetail;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverAddressDetail;
    private int orderPrice;
    private String memo;
    private int urgent;
    private int deliveryStatus;
    private int distance;
    private String freightList;
    private String arrivaltime;
    private String latitude;
    private String longitude;

    public OnDelivery() {
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

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderAddressDetail() {
        return senderAddressDetail;
    }

    public void setSenderAddressDetail(String senderAddressDetail) {
        this.senderAddressDetail = senderAddressDetail;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getFreightList() {
        return freightList;
    }

    public void setFreightList(String freightList) {
        this.freightList = freightList;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public static Creator<OnDelivery> getCREATOR() {
        return CREATOR;
    }

    protected OnDelivery(Parcel in) {
        callNum = in.readInt();
        orderNum = in.readInt();
        callTime = in.readString();
        senderName = in.readString();
        senderPhone = in.readString();
        senderAddress = in.readString();
        senderAddressDetail = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        receiverName = in.readString();
        receiverPhone = in.readString();
        receiverAddress = in.readString();
        receiverAddressDetail = in.readString();
        orderPrice = in.readInt();
        memo = in.readString();
        urgent = in.readInt();
        deliveryStatus = in.readInt();
        distance = in.readInt();
        freightList = in.readString();
        arrivaltime = in.readString();
    }

    public static final Creator<OnDelivery> CREATOR = new Creator<OnDelivery>() {
        @Override
        public OnDelivery createFromParcel(Parcel in) {
            return new OnDelivery(in);
        }

        @Override
        public OnDelivery[] newArray(int size) {
            return new OnDelivery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(callNum);
        dest.writeInt(orderNum);
        dest.writeString(callTime);
        dest.writeString(senderName);
        dest.writeString(senderPhone);
        dest.writeString(senderAddress);
        dest.writeString(senderAddressDetail);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(receiverName);
        dest.writeString(receiverPhone);
        dest.writeString(receiverAddress);
        dest.writeString(receiverAddressDetail);
        dest.writeInt(orderPrice);
        dest.writeString(memo);
        dest.writeInt(urgent);
        dest.writeInt(deliveryStatus);
        dest.writeInt(distance);
        dest.writeString(freightList);
        dest.writeString(arrivaltime);
    }
}