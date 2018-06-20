package com.nexquick.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.QuickData;
import com.nexquick.model.vo.WeatherData;

public class BigDataDAOImpl implements BigDataDAO {
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertWeatherData(WeatherData wd) {
		sqlSession.insert("nexQuickBigData.insertWeatherData", wd);
	}

	@Override
	public void insertQuickData(QuickData qd) {
		sqlSession.insert("nexQuickBigData.insertQuickData", qd);
	}
}
