package com.balbadak.nexquickapplication.vo;

public class FreightInfo {
	private int freightNum;			// 화물 번호
	private int orderNum;			// 주문 번호                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               호
	private int freightType;		// 화물 타입
	private int freightQuant;		// 화물 수량
	private int freightPrice;		// 화물 가격
	private String freightDetail;   // 기타 범주의 화물
	
	public FreightInfo() {
		super();
	}

	public FreightInfo(int freightNum, int orderNum, int freightType, int freightQuant, int freightPrice,
			String freightDetail) {
		super();
		setFreightNum(freightNum);
		setOrderNum(orderNum);
		setFreightType(freightType);
		setFreightQuant(freightQuant);
		setFreightPrice(freightPrice);
		setFreightDetail(freightDetail);
	}


	public FreightInfo(int orderNum, int freightType, int freightQuant, int freightPrice, String freightDetail) {
		super();
		setOrderNum(orderNum);
		setFreightType(freightType);
		setFreightQuant(freightQuant);
		setFreightPrice(freightPrice);
		setFreightDetail(freightDetail);
	}

	public FreightInfo(int freightType, int freightQuant, int freightPrice, String freightDetail) {
		super();
		this.freightType = freightType;
		this.freightQuant = freightQuant;
		this.freightPrice = freightPrice;
		this.freightDetail = freightDetail;
	}

	public FreightInfo(int freightType, int freightQuant, String freightDetail) {
		super();
		this.freightType = freightType;
		this.freightQuant = freightQuant;
		this.freightDetail = freightDetail;
	}

	public int getFreightNum() {
		return freightNum;
	}

	public void setFreightNum(int freightNum) {
		this.freightNum = freightNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
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

	public int getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(int freightPrice) {
		this.freightPrice = freightPrice;
	}

	public String getFreightDetail() {
		return freightDetail;
	}

	public void setFreightDetail(String freightDetail) {
		this.freightDetail = freightDetail;
	}

	@Override
	public String toString() {
		return freightType+"";

	}
}
