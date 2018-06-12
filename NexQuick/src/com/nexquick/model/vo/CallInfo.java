package com.nexquick.model.vo;

public class CallInfo {
	private int callNum;				// 콜 넘버
	private String csId;				// 고객ID
	private int qpId;					// 배정받은 QP ID
	private String senderName;			// 보내는 사람 이름(이하 3개항목 기본으로 고객정보로 입력) 		
	private String senderAddress;		// 보내는 사람 주소
	private String senderAddressDetail;		// 보내는 사람 상세주소
	private String senderPhone; 		// 보내는 사람 전화번호
	private int vehicleType;			// 운송수단
	private int totalPrice;				// 콜 총 가격
	private int payType;				// 결제 타입
	private int urgent;					// 긴급 배송 여부
	private int series;					// 묶음 배송 여부
	private int reserved;				// 예약 배송 여부
	private String reservationTime;		// 예약 시간
	private int deliveryStatus;			// 배송 상태
	private int payStatus;				// 결제 상태
	private String callTime;			// 콜 들어온 시간
	private double latitude;
	private double longitude;


	public CallInfo() {
		super();
	}

	
	public CallInfo(int callNum, String csId, int qpId, String senderName, String senderAddress,
			String senderAddressDetail, String senderPhone, int vehicleType, int totalPrice, int payType, int urgent,
			int series, int reserved, String reservationTime, int deliveryStatus, int payStatus, String callTime,
			double latitude, double longitude) {
		super();
		setCallNum(callNum);
		setCsId(csId);
		setQpId(qpId);
		setSenderName(senderName);
		setSenderAddress(senderAddress);
		setSenderAddressDetail(senderAddressDetail);
		setSenderPhone(senderPhone);
		setVehicleType(vehicleType);
		setTotalPrice(totalPrice);
		setPayType(payType);
		setUrgent(urgent);
		setSeries(series);
		setReserved(reserved);
		setReservationTime(reservationTime);
		setDeliveryStatus(deliveryStatus);
		setPayStatus(payStatus);
		setCallTime(callTime);
		setLatitude(latitude);
		setLongitude(longitude);
	}

	// newCall 시
	public CallInfo(String csId, String senderName, String senderAddress, String senderAddressDetail, String senderPhone, int vehicleType,
			int urgent, int series, int reserved, String reservationTime, double latitude, double longitude) {
		super();
		setCsId(csId);
		setSenderName(senderName);
		setSenderAddress(senderAddress);
		setSenderAddressDetail(senderAddressDetail);
		setSenderPhone(senderPhone);
		setVehicleType(vehicleType);
		setUrgent(urgent);
		setSeries(series);
		setReserved(reserved);
		setReservationTime(reservationTime);
		setLatitude(latitude);
		setLongitude(longitude);
	}


	public int getCallNum() {
		return callNum;
	}

	public void setCallNum(int callNum) {
		this.callNum = callNum;
	}

	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	public int getQpId() {
		return qpId;
	}

	public void setQpId(int qpId) {
		this.qpId = qpId;
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

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getUrgent() {
		return urgent;
	}

	public void setUrgent(int urgent) {
		this.urgent = urgent;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public String getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}

	public int getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(int deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	
	public String getSenderAddressDetail() {
		return senderAddressDetail;
	}

	public void setSenderAddressDetail(String senderAddressDetail) {
		this.senderAddressDetail = senderAddressDetail;
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
