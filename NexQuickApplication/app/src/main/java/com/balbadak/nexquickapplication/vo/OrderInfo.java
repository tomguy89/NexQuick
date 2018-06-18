package com.balbadak.nexquickapplication.vo;

import java.io.Serializable;

public class OrderInfo implements Serializable{
	private int orderNum;					// 주문번호
	private int callNum;					// 콜 번호
	private String receiverName;			// 받는 사람 이름
	private String receiverAddress;			// 받는 사람 주소
	private String receiverAddressDetail;			// 받는 사람 상세주소
	private String receiverPhone;			// 받는 사람 전화번호
	private String memo;					// 배송 메시지
	private int orderPrice;					// 주문 가격
	private int isGet;						// 배송 상태
	private String arrivalTime;				// 도착 시간
	private double distance;
	private double latitude;
	private double longitude;
	
	public OrderInfo() {
		super();
	}

	
	public OrderInfo(int callNum, String receiverName, String receiverAddress, String receiverAddressDetail, String receiverPhone, String memo,
			int orderPrice, double distance, double latitude, double longitude) {
		super();
		setCallNum(callNum);
		setReceiverName(receiverName);
		setReceiverAddress(receiverAddress);
		setReceiverAddressDetail(receiverAddressDetail);
		setReceiverPhone(receiverPhone);
		setMemo(memo);
		setOrderPrice(orderPrice);
		setDistance(distance);
		setLatitude(latitude);
		setLongitude(longitude);
	}

	public String getReceiverAddressDetail() {
		return receiverAddressDetail;
	}

	public void setReceiverAddressDetail(String receiverAddressDetail) {
		this.receiverAddressDetail = receiverAddressDetail;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getCallNum() {
		return callNum;
	}

	public void setCallNum(int callNum) {
		this.callNum = callNum;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getIsGet() {
		return isGet;
	}

	public void setIsGet(int isGet) {
		this.isGet = isGet;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
