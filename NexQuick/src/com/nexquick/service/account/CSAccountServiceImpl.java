package com.nexquick.service.account;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.dao.CSInfoDAO;
import com.nexquick.model.vo.CSDevice;
import com.nexquick.model.vo.CSInfo;

public class CSAccountServiceImpl implements CSAccountService {

	private CSInfoDAO csInfoDao;
	public void setCsInfoDao(CSInfoDAO csInfoDao) {
		this.csInfoDao = csInfoDao;
	}

	@Override
	public CSInfo csSignIn(String csId, String csPassword, String token) {
		CSInfo csInfo = csInfoDao.selectCS(csId);
		if(csInfo!=null) {
			if(csInfo.getCsPassword().equals(csPassword)) {
				if(token!=null && token.trim().length() != 0) {
					csInfoDao.lastSignedInDevice(new CSDevice(csId, token));
				}
				return csInfo;
			}
		}
		return null;
	}

	@Override
	public boolean csSignUp(CSInfo csInfo) {
		return csInfoDao.createCS(csInfo);
	}
	
	@Override
	public boolean csIdDuplicateCheck(String csId) {
		return csInfoDao.selectCS(csId)==null;
	}

	@Override
	public boolean csModify(CSInfo csInfo) {
		return csInfoDao.updateCS(csInfo);
	}

	@Override
	public List<CSInfo> csAllList() {
		return csInfoDao.selectCSList();
	}
	
	@Override
	public List<CSInfo> csAllListByName(HashMap<String, Object> condition) {
		return csInfoDao.selectCSListByName(condition);
	}

	@Override
	public void deleteDeviceInfo(String csId) {
		csInfoDao.deleteDeviceInfo(csId);
	}
	
	
}
