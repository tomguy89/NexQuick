package com.nexquick.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.FreightInfo;

public class FreightInfoDAOImpl implements FreightInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createFreight(FreightInfo freightInfo) {
		return sqlSession.insert("freightInfo.createFreight", freightInfo)>0;
	}

	@Override
	public boolean updateFreight(FreightInfo freightInfo) {
		return sqlSession.update("freightInfo.updateFreight", freightInfo)>0;
	}

	@Override
	public boolean deleteFreight(int freightNum) {
		return sqlSession.delete("freightInfo.deleteFreight", freightNum)>0;
	}
	
	@Override
	public boolean deleteFreights(int orderNum) {
		return sqlSession.delete("freightInfo.deleteFreights", orderNum)>0;
	}

	@Override
	public FreightInfo selectFreight(int freightNum) {
		return sqlSession.selectOne("freightInfo.selectFreight", freightNum);
	}

	@Override
	public List<FreightInfo> selectFreightList() {
		return sqlSession.selectList("freightInfo.selectFreightList");
	}

	@Override
	public List<FreightInfo> selectFreightList(int orderNum) {
		return sqlSession.selectList("freightInfo.selectFreightListByOnum", orderNum);
	}

	@Override
	public void deletePastFreights() {
		sqlSession.delete("freightInfo.deletePastFreights");
	}

}
