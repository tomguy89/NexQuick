package com.nexquick.service.account;

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
	
}
