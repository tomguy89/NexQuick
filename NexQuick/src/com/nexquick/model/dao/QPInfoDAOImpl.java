package com.nexquick.model.dao;

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

	@Override
	public List<QPInfo> selectQPList(int qpVehicleType) {
		return sqlSession.selectList("qpInfo.selectQPListByVehicle", qpVehicleType);
	}

}
