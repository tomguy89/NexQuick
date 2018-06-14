package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.CSDevice;
import com.nexquick.model.vo.CSInfo;

public class CSInfoDAOImpl implements CSInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createCS(CSInfo csInfo) {
		return sqlSession.insert("csInfo.createCS", csInfo)>0;
	}

	@Override
	public boolean updateCS(CSInfo csInfo) {
		return sqlSession.update("csInfo.updateCS", csInfo)>0;
	}

	@Override
	public CSInfo selectCS(String csId) {
		return sqlSession.selectOne("csInfo.selectCS", csId);
	}

	@Override
	public CSInfo selectCSbyPhone(String csPhone) {
		return sqlSession.selectOne("csInfo.selectCSbyPhone", csPhone);
	}

	@Override
	public List<CSInfo> selectCSList() {
		return sqlSession.selectList("csInfo.selectCSList");
	}
	
	@Override
	public List<CSInfo> selectCSListByName(HashMap<String, Object> condition) {
		return sqlSession.selectList("csInfo.selectCSListByName", condition);
	}

	@Override
	public List<CSInfo> selectCSList(String csBusinessNumber) {
		return sqlSession.selectList("csInfo.selectCSListByBnum", csBusinessNumber);
	}

	@Override
	public boolean lastSignedInDevice(CSDevice csDevice) {
		return sqlSession.update("csInfo.lastSignedInDevice", csDevice)>0;
	}

	@Override
	public String selectCSDevice(String csId) {
		return sqlSession.selectOne("csInfo.selectCSDevice", csId);
	}

	@Override
	public void deleteDeviceInfo(String csId) {
		sqlSession.update("csInfo.deleteDeviceInfo", csId);
	}

}
