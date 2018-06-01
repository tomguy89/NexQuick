package com.nexquick.model.vo;

public class PriceInfo {
	private int freightType;
	private String freightName;
	private int freeCount;
	private int price;
	
	public PriceInfo() {
		super();
	}

	public PriceInfo(int freightType, String freightName, int freeCount, int price) {
		super();
		this.freightType = freightType;
		this.freightName = freightName;
		this.freeCount = freeCount;
		this.price = price;
	}

	public int getFreightType() {
		return freightType;
	}

	public void setFreightType(int freightType) {
		this.freightType = freightType;
	}

	public String getFreightName() {
		return freightName;
	}

	public void setFreightName(String freightName) {
		this.freightName = freightName;
	}

	public int getFreeCount() {
		return freeCount;
	}
	
	public void setFreeCount(int freeCount) {
		this.freeCount = freeCount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	
}
