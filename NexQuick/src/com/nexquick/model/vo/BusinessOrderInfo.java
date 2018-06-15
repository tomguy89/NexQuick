package com.nexquick.model.vo;

public class BusinessOrderInfo {

	private int orderNum;
	private String csId;
	private String csName;
	private String csBusinessName;
	private String csDepartment;
	private String senderName;
	private String senderAddress;
	private String senderAddressDetail;
	private String callTime;
	private int orderPrice;
	private int payType;

	
	
	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	public String getCsBusinessName() {
		return csBusinessName;
	}

	public void setCsBusinessName(String csBusinessName) {
		this.csBusinessName = csBusinessName;
	}

	public String getCsDepartment() {
		return csDepartment;
	}

	public void setCsDepartment(String csDepartment) {
		this.csDepartment = csDepartment;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

}
