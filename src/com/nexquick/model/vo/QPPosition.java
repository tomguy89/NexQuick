package com.nexquick.model.vo;

public class QPPosition { 		
	private int qpId;			// QP ID
	private long qpLatitude;	// QP 위치 (위도)
	private long qpLongitude;	// QP 위치 (경도)
	private String bCode;		// 지역번호
	private String hCode;
	
	public QPPosition() {
		super();
	}

	public QPPosition(int qpId, long qpLatitude, long qpLongitude, String bCode, String hCode) {
		super();
		this.qpId = qpId;
		this.qpLatitude = qpLatitude;
		this.qpLongitude = qpLongitude;
		this.bCode = bCode;
		this.hCode = hCode;
	}

	public int getQpId() {
		return qpId;
	}

	public void setQpId(int qpId) {
		this.qpId = qpId;
	}

	public long getQpLatitude() {
		return qpLatitude;
	}

	public void setQpLatitude(long qpLatitude) {
		this.qpLatitude = qpLatitude;
	}

	public long getQpLongitude() {
		return qpLongitude;
	}

	public void setQpLongitude(long qpLongitude) {
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
}
