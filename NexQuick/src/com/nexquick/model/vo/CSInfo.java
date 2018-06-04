package com.nexquick.model.vo;

public class CSInfo {
	private String csId;				// 고객 아이디
	private String csPassword;			// 비밀번호
	private String csName;				// 이름
	private String csPhone;				// 고객 전화번호
	private int csType;					// 고객 타입
	private String csBusinessName;		// 사업장 이름
	private String csBusinessNumber;	// 사업자 등록번호
	private String csDepartment;		// 부서
	private int csMilege;				// 마일리지
	private int csGrade;				// 고객 등급
	
	private CallInfo callInfo;
	
	public CSInfo() {
		super();
	}

	public CSInfo(String csId, String csPassword, String csName, String csPhone, int csType, String csBusinessName,
			String csBusinessNumber, String csDepartment, int csMilege, int csGrade) {
		super();
		setCsId(csId);
		setCsPassword(csPassword);
		setCsName(csName);
		setCsPhone(csPhone);
		setCsType(csType);
		setCsBusinessName(csBusinessName);
		setCsBusinessNumber(csBusinessNumber);
		setCsDepartment(csDepartment);
		setCsMilege(csMilege);
		setCsGrade(csGrade);
	}
	
	public CSInfo(String csId, String csPassword, String csName, String csPhone, int csType, String csBusinessName,
			String csBusinessNumber, String csDepartment) {
		super();
		setCsId(csId);
		setCsPassword(csPassword);
		setCsName(csName);
		setCsPhone(csPhone);
		setCsType(csType);
		setCsBusinessName(csBusinessName);
		setCsBusinessNumber(csBusinessNumber);
		setCsDepartment(csDepartment);
	}

	public CSInfo(String csId, String csPassword, String csName, String csPhone, int csType, String csBusinessName,
			String csBusinessNumber) {
		super();
		setCsId(csId);
		setCsPassword(csPassword);
		setCsName(csName);
		setCsPhone(csPhone);
		setCsType(csType);
		setCsBusinessName(csBusinessName);
		setCsBusinessNumber(csBusinessNumber);
	}

	public CSInfo(String csId, String csPassword, String csName, String csPhone, int csType) {
		super();
		setCsId(csId);
		setCsPassword(csPassword);
		setCsName(csName);
		setCsPhone(csPhone);
		setCsType(csType);
	}

	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	public String getCsPassword() {
		return csPassword;
	}

	public void setCsPassword(String csPassword) {
		this.csPassword = csPassword;
	}

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getCsPhone() {
		return csPhone;
	}

	public void setCsPhone(String csPhone) {
		this.csPhone = csPhone;
	}

	public int getCsType() {
		return csType;
	}

	public void setCsType(int csType) {
		this.csType = csType;
	}

	public String getCsBusinessName() {
		return csBusinessName;
	}

	public void setCsBusinessName(String csBusinessName) {
		this.csBusinessName = csBusinessName;
	}

	public String getCsBusinessNumber() {
		return csBusinessNumber;
	}

	public void setCsBusinessNumber(String csBusinessNumber) {
		this.csBusinessNumber = csBusinessNumber;
	}

	public String getCsDepartment() {
		return csDepartment;
	}

	public void setCsDepartment(String csDepartment) {
		this.csDepartment = csDepartment;
	}

	public int getCsMilege() {
		return csMilege;
	}

	public void setCsMilege(int csMilege) {
		this.csMilege = csMilege;
	}

	public int getCsGrade() {
		return csGrade;
	}

	public void setCsGrade(int csGrade) {
		this.csGrade = csGrade;
	}

	public CallInfo getCallInfo() {
		return callInfo;
	}

	public void setCallInfo(CallInfo callInfo) {
		this.callInfo = callInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CSInfo [csId=");
		builder.append(csId);
		builder.append(", csPassword=");
		builder.append(csPassword);
		builder.append(", csName=");
		builder.append(csName);
		builder.append(", csPhone=");
		builder.append(csPhone);
		builder.append(", csType=");
		builder.append(csType);
		builder.append(", csBusinessName=");
		builder.append(csBusinessName);
		builder.append(", csBusinessNumber=");
		builder.append(csBusinessNumber);
		builder.append(", csDepartment=");
		builder.append(csDepartment);
		builder.append(", csMilege=");
		builder.append(csMilege);
		builder.append(", csGrade=");
		builder.append(csGrade);
		builder.append(", callInfo=");
		builder.append(callInfo);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
