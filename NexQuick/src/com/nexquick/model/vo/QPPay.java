package com.nexquick.model.vo;

public class QPPay {
	
	private String qpAccount;
	private String qpBank;
	private int money;
	public QPPay() {
		super();
	}
	public QPPay(String qpAccount, String qpBank, int money) {
		super();
		setQpAccount(qpAccount);
		setQpBank(qpBank);
		setMoney(money);
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
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
	
	
}
