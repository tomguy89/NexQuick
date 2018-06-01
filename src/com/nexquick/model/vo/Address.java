package com.nexquick.model.vo;

public class Address {
	private String fullAddr;
	private String country;
	private String sido;
	private String sigugun;
	private String dongmyun;
	private String ri;
	private String rest;
	private boolean isRoadAddress;
	private String longitude; //경도 x
	private String latitude;  //위도 y
	private String bCode;
	private String hCode;
	
	public Address() {
		super();
	}

	public Address(String fullAddr, String country, String sido, String sigugun, String dongmyun, String ri,
			String rest, boolean isRoadAddress, String longitude, String latitude) {
		super();
		this.fullAddr = fullAddr;
		this.country = country;
		this.sido = sido;
		this.sigugun = sigugun;
		this.dongmyun = dongmyun;
		this.ri = ri;
		this.rest = rest;
		this.isRoadAddress = isRoadAddress;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getFullAddr() {
		return fullAddr;
	}

	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getSigugun() {
		return sigugun;
	}

	public void setSigugun(String sigugun) {
		this.sigugun = sigugun;
	}

	public String getDongmyun() {
		return dongmyun;
	}

	public void setDongmyun(String dongmyun) {
		this.dongmyun = dongmyun;
	}

	public String getRi() {
		return ri;
	}

	public void setRi(String ri) {
		this.ri = ri;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public boolean isRoadAddress() {
		return isRoadAddress;
	}

	public void setRoadAddress(boolean isRoadAddress) {
		this.isRoadAddress = isRoadAddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getbCode() {
		return bCode;
	}

	public void setbCode(String bCode) {
		this.bCode = bCode;
	}

	public String gethCode() {
		return hCode;
	}

	public void sethCode(String hCode) {
		this.hCode = hCode;
	}



}
