package com.balbadak.nexquickpro.vo;

public class FavoriteInfo {
	private int favoriteId;			// 주소 ID
	private String csId;		// 고객 ID
	private int addressType;	// 주소 타입(1.기본출발지, 2.출발지, 3.배송지)
	private String address;		// 주소
	private String addrDetail;		// 상세주소
	private String receiverName;
	private String receiverPhone;
	
	public FavoriteInfo() {
		super();
	}

	
	
	
	public FavoriteInfo(String csId, int addressType, String address, String addrDetail,
			String receiverName, String receiverPhone) {
		super();
		this.csId = csId;
		this.addressType = addressType;
		this.address = address;
		this.addrDetail = addrDetail;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
	}
	
	public FavoriteInfo(int favoriteId, String csId, int addressType, String address, String addrDetail,
			String receiverName, String receiverPhone) {
		super();
		this.favoriteId = favoriteId;
		this.csId = csId;
		this.addressType = addressType;
		this.address = address;
		this.addrDetail = addrDetail;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
	}




	public FavoriteInfo(int favoriteId, String csId, int addressType, String address, String receiverName, String receiverPhone) {
		super();
		setFavoriteId(favoriteId);
		setCsId(csId);
		setAddressType(addressType);
		setAddress(address);
		setReceiverName(receiverName);
		setReceiverPhone(receiverPhone);
	}

	public String getAddrDetail() {
		return addrDetail;
	}




	public void setAddrDetail(String addressDetail) {
		this.addrDetail = addressDetail;
	}




	public int getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	public int getAddressType() {
		return addressType;
	}

	public void setAddressType(int addressType) {
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@Override
	public String toString() {
		return getReceiverName() + ":" + getAddress();
	}
}
