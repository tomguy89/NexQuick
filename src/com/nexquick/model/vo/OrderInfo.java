package com.nexquick.model.vo;

public class OrderInfo {
	private int orderNum;					// 주문번호
	private int callNum;					// 콜 번호
	private String receiverName;			// 받는 사람 이름
	private String receiverAddress;			// 받는 사람 주소
	private String receiverPhone;			// 받는 사람 전화번호
	private String memo;					// 배송 메시지
	private int orderPrice;					// 주문 가격
	private int isGet;						// 배송 상태
	private String arrivalTime;				// 도착 시간
	
	public OrderInfo() {
		super();
	}

	public OrderInfo(int orderNum, int callNum, String receiverName, String receiverAddress, String receiverPhone,
			String memo, int orderPrice, String arrivalTime) {
		super();
		setOrderNum(orderNum);
		setCallNum(callNum);
		setReceiverName(receiverName);
		setReceiverAddress(receiverAddress);
		setReceiverPhone(receiverPhone);
		setMemo(memo);
		setOrderPrice(orderPrice);
		setArrivalTime(arrivalTime);
	}
	
	public OrderInfo(int callNum, String receiverName, String receiverAddress, String receiverPhone, String memo) {
		super();
		setCallNum(callNum);
		setReceiverName(receiverName);
		setReceiverAddress(receiverAddress);
		setReceiverPhone(receiverPhone);
		setMemo(memo);
	}
	
	public OrderInfo(int callNum, String receiverName, String receiverAddress, String receiverPhone, String memo,
			int orderPrice) {
		super();
		setCallNum(callNum);
		setReceiverName(receiverName);
		setReceiverAddress(receiverAddress);
		setReceiverPhone(receiverPhone);
		setMemo(memo);
		setOrderPrice(orderPrice);
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
	
	
}
