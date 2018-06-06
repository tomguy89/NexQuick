package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.FavoriteInfo;

public class FavoriteInfoDAOImpl implements FavoriteInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createAddress(FavoriteInfo addressInfo) {
		return sqlSession.insert("favoriteInfo.createFavorite", addressInfo)>0;
	}

	@Override
	public boolean updateAddress(FavoriteInfo addressInfo) {
		return sqlSession.update("favoriteInfo.updateAddress", addressInfo)>0;
	}

	@Override
	public boolean deleteAddress(int addId) {
		return sqlSession.delete("favoriteInfo.updateAddress", addId)>0;
	}

	@Override
	public FavoriteInfo selectAddress(int addId) {
		return sqlSession.selectOne("favoriteInfo.selectAddress", addId);
	}

	@Override
	public List<FavoriteInfo> selectAddressList(String csId, int addressType) {
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("csId", csId);
		condition.put("addressType", addressType);
		return sqlSession.selectList("favoriteInfo.selectFavoriteList", condition);
	}

	
	
	
}
