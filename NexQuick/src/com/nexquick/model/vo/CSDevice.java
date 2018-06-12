package com.nexquick.model.vo;

public class CSDevice {
	private String csId;
	private String csToken;
	
	public CSDevice(String csId, String csToken) {
		super();
		this.csId = csId;
		this.csToken = csToken;
	}

	public String getCsId() {
		return csId;
	}
	
	public void setCsId(String csId) {
		this.csId = csId;
	}
	
	public String getCsToken() {
		return csToken;
	}
	
	public void setCsToken(String csToken) {
		this.csToken = csToken;
	}
	
	
}
