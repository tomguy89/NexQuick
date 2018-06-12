package com.nexquick.model.vo;

public class QPPosition { 		
	private int qpId;			// QP ID
	private double qpLatitude;	// QP 위치 (위도)
	private double qpLongitude;	// QP 위치 (경도)
	private String bCode;		// 지역번호
	private String hCode;
	private String connectToken;
	private int qpStatus;
	private int now;
	
	public QPPosition() {
		super();
	}

	public QPPosition(int qpId, double qpLatitude, double qpLongitude, String bCode, String hCode,
			String connectToken) {
		super();
		this.qpId = qpId;
		this.qpLatitude = qpLatitude;
		this.qpLongitude = qpLongitude;
		this.bCode = bCode;
		this.hCode = hCode;
		this.connectToken = connectToken;
	}

	public int getQpId() {
		return qpId;
	}

	public void setQpId(int qpId) {
		this.qpId = qpId;
	}

	public double getQpLatitude() {
		return qpLatitude;
	}

	public void setQpLatitude(double qpLatitude) {
		this.qpLatitude = qpLatitude;
	}

	public double getQpLongitude() {
		return qpLongitude;
	}

	public void setQpLongitude(double qpLongitude) {
		this.qpLongitude = qpLongitude;
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

	public String getConnectToken() {
		return connectToken;
	}

	public void setConnectToken(String connectToken) {
		this.connectToken = connectToken;
	}

	public int getQpStatus() {
		return qpStatus;
	}

	public void setQpStatus(int qpStatus) {
		this.qpStatus = qpStatus;
	}

	public int getNow() {
		return now;
	}

	public void setNow(int now) {
		this.now = now;
	}
	
}
