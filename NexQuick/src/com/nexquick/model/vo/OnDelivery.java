package com.nexquick.model.vo;

public class OnDelivery {
	private int callNum;
	private int orderNum;
	private String callTime;
	private String senderName;
	private String senderAddress;
	private String senderAddressDetail;
	private String receiverName;
	private String receiverAddress;
	private String receiverAddressDetail;
	private int orderPrice;
	private int urgent;
	private int deliveryStatus;
	
	public OnDelivery() {
		super();
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddressDetail() {
		return senderAddressDetail;
	}

	public void setSenderAddressDetail(String senderAddressDetail) {
		this.senderAddressDetail = senderAddressDetail;
	}

	public String getReceiverAddressDetail() {
		return receiverAddressDetail;
	}

	public void setReceiverAddressDetail(String receiverAddressDetail) {
		this.receiverAddressDetail = receiverAddressDetail;
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

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
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
	
	
}
