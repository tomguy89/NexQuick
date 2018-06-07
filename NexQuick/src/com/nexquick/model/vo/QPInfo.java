package com.nexquick.model.vo;

public class QPInfo {
	private int qpId;					// QuickPro ID
	private String qpPassword;			// 비밀번호
	private String qpName;				// QP 이름
	private String qpPhone;				// QP 전화번호
	private String qpLicense;			// QP 면허정보
	private int qpVehicleType;			// QP 운송수단
	private int qpDeposit;				// QP 선입금액
	private String qpProfile;			// QP 기타 상세정보(사진)
	private String qpAccount;
	private String qpBank;
	
	public QPInfo() {
		super();
	}

	public QPInfo(int qpId, String qpPassword, String qpName, String qpPhone, String qpLicense, int qpVehicleType,
			int qpDeposit, String qpProfile, String qpAccount, String qpBank) {
		super();
		this.qpId = qpId;
		this.qpPassword = qpPassword;
		this.qpName = qpName;
		this.qpPhone = qpPhone;
		this.qpLicense = qpLicense;
		this.qpVehicleType = qpVehicleType;
		this.qpDeposit = qpDeposit;
		this.qpProfile = qpProfile;
		this.qpAccount = qpAccount;
		this.qpBank = qpBank;
	}

	public QPInfo(int qpId, String qpPassword, String qpName, String qpPhone, String qpLicense, int qpVehicleType,
			int qpDeposit, String qpProfile) {
		super();
		setQpId(qpId);
		setQpPassword(qpPassword);
		setQpName(qpName);
		setQpPhone(qpPhone);
		setQpLicense(qpLicense);
		setQpVehicleType(qpVehicleType);
		setQpDeposit(qpDeposit);
		setQpProfile(qpProfile);
	}
	
	public QPInfo(String qpPassword, String qpName, String qpPhone, String qpLicense, int qpVehicleType,
			String qpProfile, String qpAccount, String qpBank) {
		super();
		this.qpPassword = qpPassword;
		this.qpName = qpName;
		this.qpPhone = qpPhone;
		this.qpLicense = qpLicense;
		this.qpVehicleType = qpVehicleType;
		this.qpProfile = qpProfile;
		this.qpAccount = qpAccount;
		this.qpBank = qpBank;
	}

	public int getQpId() {
		return qpId;
	}

	public void setQpId(int qpId) {
		this.qpId = qpId;
	}

	public String getQpPassword() {
		return qpPassword;
	}

	public void setQpPassword(String qpPassword) {
		this.qpPassword = qpPassword;
	}

	public String getQpName() {
		return qpName;
	}

	public void setQpName(String qpName) {
		this.qpName = qpName;
	}

	public String getQpPhone() {
		return qpPhone;
	}

	public void setQpPhone(String qpPhone) {
		this.qpPhone = qpPhone;
	}

	public String getQpLicense() {
		return qpLicense;
	}

	public void setQpLicense(String qpLicense) {
		this.qpLicense = qpLicense;
	}

	public int getQpVehicleType() {
		return qpVehicleType;
	}

	public void setQpVehicleType(int qpVehicleType) {
		this.qpVehicleType = qpVehicleType;
	}

	public int getQpDeposit() {
		return qpDeposit;
	}

	public void setQpDeposit(int qpDeposit) {
		this.qpDeposit = qpDeposit;
	}

	public String getQpProfile() {
		return qpProfile;
	}

	public void setQpProfile(String qpProfile) {
		this.qpProfile = qpProfile;
	}

	public String getQpAccount() {
		return qpAccount;
	}

	public void setQpAccount(String qpAccount) {
		this.qpAccount = qpAccount;
	}

	public String getQpBank() {
		return qpBank;
	}

	public void setQpBank(String qpBank) {
		this.qpBank = qpBank;
	}
	
	
}
