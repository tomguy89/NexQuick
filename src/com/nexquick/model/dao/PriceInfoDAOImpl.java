package com.nexquick.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.PriceInfo;

public class PriceInfoDAOImpl implements PriceInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public boolean createPrice(PriceInfo priceInfo) {
		return sqlSession.insert("priceInfo.createPrice", priceInfo)>0;
	}

	@Override
	public boolean updatePrice(PriceInfo priceInfo) {
		return sqlSession.update("priceInfo.updatePrice", priceInfo)>0;
	}

	@Override
	public boolean deletePrice(int freightType) {
		return sqlSession.delete("priceInfo.deletePrice", freightType)>0;
	}

	@Override
	public PriceInfo selectPrice(int freightType) {
		return sqlSession.selectOne("priceInfo.selectPrice", freightType);
	}

}
