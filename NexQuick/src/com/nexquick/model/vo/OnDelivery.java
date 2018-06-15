
package com.nexquick.model.vo;

public class OnDelivery {
	private int callNum;
	private int orderNum;
	private String callTime;
	private String senderName;
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
	private int freightType;
	private int freightQuant;
	private int freightPrice;
	private String freightList;
	
	public OnDelivery() {
		super();
	}
	
	public int getFreightPrice() {
		return freightPrice;
	}
	
	public void setFreightPrice(int freightPrice) {
		this.freightPrice = freightPrice;
	}
	
	public int getFreightType() {
		return freightType;
	}
	
	public void setFreightType(int freightType) {
		this.freightType = freightType;
	}
	
	public int getFreightQuant() {
		return freightQuant;
	}
	
	public void setFreightQuant(int freightQuant) {
		this.freightQuant = freightQuant;
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
	
	public String getReceiverPhone() {
		return receiverPhone;
	}
	
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
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
	
	public String getFreightList() {
		return freightList;
	}
	
	public void setFreightList(String freightList) {
		this.freightList = freightList;
	}
	
}