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
	
//	0615 김민규 수정
	@Override
	public List<CSInfo> csAllListBySearch(HashMap<String, Object> condition) {
		return csInfoDao.selectCSListBySearch(condition);
	}

	@Override
	public void deleteDeviceInfo(String csId) {
		csInfoDao.deleteDeviceInfo(csId);
	}
	
//	0615 김민규추가
	@Override
	public List<String> getBusinessNames(String name) {
		return csInfoDao.getBusinessNames(name);
	}
	
//	0615 김민규추가
	@Override
	public List<String> getDepartments(HashMap<String, Object> condition) {
		return csInfoDao.getDepartments(condition);
	}

//	0615 김민규추가
	@Override
	public CSInfo getCSInfo(String csId) {
		return csInfoDao.selectCS(csId);
	}

//	0615 김민규추가
	@Override
	public boolean csGradeModify(CSInfo csInfo) {
		return csInfoDao.updateCSGrade(csInfo);
	}
	
	
//	0615 김민규추가(20:00)
	@Override
	public List<CSInfo> getCSByCorporate(HashMap<String, Object> condition) {
		return csInfoDao.getCSByCorporate(condition);
	}
	
}
