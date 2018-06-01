package com.nexquick.model.vo;

public class QPPosition { 		
	private int qpId;			// QP ID
	private long qpLatitude;	// QP 위치 (위도)
	private long qpLongitude;	// QP 위치 (경도)
	private int bCode;		// 지역번호
	private int hCode;
	
	public QPPosition() {
		super();
	}

	public QPPosition(int qpId, long qpLatitude, long qpLongitude, int bCode, int hCode) {
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

	public int getbCode() {
		return bCode;
	}

	public void setbCode(int bCode) {
		this.bCode = bCode;
	}

	public int gethCode() {
		return hCode;
	}

	public void sethCode(int hCode) {
		this.hCode = hCode;
	}


	
}
