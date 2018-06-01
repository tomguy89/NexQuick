package com.nexquick.model.vo;

public class QPScore {
	
	private int scoreNum;		// 평점번호
	private int qpId;			// QP ID
	private int callNum;		// 콜 번호
	private int orderNum;		// 오더번호
	private int score;			// 평점
	
	public QPScore() {
		super();
	}
	
	public QPScore(int scoreNum, int qpId, int callNum, int orderNum, int score) {
		super();
		setScoreNum(scoreNum);
		setQpId(qpId);
		setCallNum(callNum);
		setOrderNum(orderNum);
		setScore(score);
	}

	public int getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(int scoreNum) {
		this.scoreNum = scoreNum;
	}

	public int getQpId() {
		return qpId;
	}

	public void setQpId(int qpId) {
		this.qpId = qpId;
	}

	public int getCallNum() {
		return callNum;
	}

	public void setCallNum(int callNum) {
		this.callNum = callNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}
