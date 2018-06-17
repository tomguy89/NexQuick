package com.nexquick.service.account;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.dao.QPInfoDAO;
import com.nexquick.model.vo.QPInfo;

public class QPAccountServiceImpl implements QPAccountService {

	private QPInfoDAO qpInfoDao;
	public void setQpInfoDao(QPInfoDAO qpInfoDao) {
		this.qpInfoDao = qpInfoDao;
	}

	@Override
	public QPInfo qpSignIn(String qpPhone, String qpPassword) {
		QPInfo qpInfo = qpInfoDao.selectQP(qpPhone);
		if (qpInfo == null) return null;
		if(qpInfo.getQpPassword().equals(qpPassword)) return qpInfo;
		else return null;
	}

	@Override
	public boolean qpSignUp(QPInfo qpInfo) {
		boolean result = qpInfoDao.createQP(qpInfo);
		if(result) qpInfoDao.createQPAccount(qpInfo);
		return result;
	}

	@Override
	public boolean qpPhoneDuplicateCheck(String qpPhone) {
		return qpInfoDao.selectQP(qpPhone)==null;
	}

	@Override
	public boolean qpModify(QPInfo qpInfo) {
		return qpInfoDao.updateQP(qpInfo);
	}
	
	@Override
	public List<QPInfo> qpAllList() {
		return qpInfoDao.selectQPList();
	}
	
//	0615 김민규 수정
	@Override
	public List<QPInfo> qpAllListSearch(HashMap<String, Object> condition) {
		return qpInfoDao.selectQPListSearch(condition);
	}
	
	//0612 이은진 추가.

	@Override
	public QPInfo selectQPAccountById(int qpId) {
		// TODO Auto-generated method stub
		return qpInfoDao.selectQPAccountById(qpId);
	}

//	0613 김민규추가
	@Override
	public QPInfo getQPByCallNum(int callNum) {
		return qpInfoDao.selectQPByCallNum(callNum);
	}
	
	//0614 이은진 추가
	@Override
	public void updateProfile(String qpProfile, String qpPhone) {
		qpInfoDao.updateProfile(qpProfile, qpPhone);
		
	}

	@Override
	public void updateLicense(String qpLicense, String qpPhone) {
		// TODO Auto-generated method stub
		qpInfoDao.updateLicense(qpLicense, qpPhone);
	}
	
	@Override
	public QPInfo selectQP(int qpId) {
		return qpInfoDao.selectQP(qpId);
	}
	
	@Override
	public boolean qpAccountModify(QPInfo qpInfo) {
		return qpInfoDao.updateQPAccount(qpInfo);
	}

}
