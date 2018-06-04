package com.nexquick.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.QPScore;

public class QPScoreDAOImpl implements QPScoreDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createScore(QPScore qpScore) {
		return sqlSession.insert("qpScore.createScore", qpScore)>0;
	}

	@Override
	public QPScore scoreByCallNum(int callNum) {
		return sqlSession.selectOne("qpScore.scoreByCallNum", callNum);
	}

	@Override
	public QPScore scoreByOrderNum(int orderNum) {
		return sqlSession.selectOne("qpScore.scoreByOrderNum", orderNum);
	}

	@Override
	public List<QPScore> selectQPScore(int qpId) {
		return sqlSession.selectList("qpScore.selectQPScore", qpId);
	}

}
