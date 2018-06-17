package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.QPInfo;

public class QPInfoDAOImpl implements QPInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public boolean createQP(QPInfo qpInfo) {
		return sqlSession.insert("qpInfo.createQP", qpInfo)>0;
	}
	
	@Override
	public void createQPAccount(QPInfo qpInfo) {
		sqlSession.insert("qpInfo.createQPAccount", qpInfo);
	}

	@Override
	public boolean updateQP(QPInfo qpInfo) {
		return sqlSession.update("qpInfo.updateQP", qpInfo)>0;
	}

	@Override
	public QPInfo selectQP(int qpId) {
		return sqlSession.selectOne("qpInfo.selectQPById", qpId);
	}

	@Override
	public QPInfo selectQP(String qpPhone) {
		return sqlSession.selectOne("qpInfo.selectQPByPhone", qpPhone);
	}

	@Override
	public List<QPInfo> selectQPList() {
		return sqlSession.selectList("qpInfo.selectQPList");
	}
	
//	0615 김민규 수정
	@Override
	public List<QPInfo> selectQPListSearch(HashMap<String, Object> condition) {
		return sqlSession.selectList("qpInfo.selectQPListSearch", condition);
	}
	
	
	@Override
	public List<QPInfo> selectQPList(int qpVehicleType) {
		return sqlSession.selectList("qpInfo.selectQPListByVehicle", qpVehicleType);
	}

	@Override
	public QPInfo selectQPAccountById(int qpId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("qpInfo.selectQPAccountById", qpId);
	}

//	0613 김민규추가
	@Override
	public QPInfo selectQPByCallNum(int callNum) {
		return sqlSession.selectOne("qpInfo.selectQPInfoByCallNum", callNum);
	}
	
	//0614 이은진 추가
	@Override
	public void updateProfile(String qpProfile, String qpPhone) {
		HashMap<String, String> condition = new HashMap<>();
		condition.put("qpProfile",qpProfile);
		condition.put("qpPhone", qpPhone);
		sqlSession.update("qpInfo.updateProfile", condition);
		
	}

	@Override
	public void updateLicense(String qpLicense, String qpPhone) {
		HashMap<String, String> condition = new HashMap<>();
		condition.put("qpLicense",qpLicense);
		condition.put("qpPhone", qpPhone);
		sqlSession.update("qpInfo.updateLicense", condition);
	}

	
	@Override
	public boolean updateQPAccount(QPInfo qpInfo) {
		return sqlSession.update("qpInfo.updateQPAccount", qpInfo)>0;
	}

	
}
