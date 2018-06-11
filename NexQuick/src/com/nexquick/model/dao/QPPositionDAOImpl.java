package com.nexquick.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.QPPosition;

public class QPPositionDAOImpl implements QPPositionDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createQPPosition(QPPosition qpPosition) {
		return sqlSession.insert("qpPosition.createQP", qpPosition)>0;
	}

	@Override
	public boolean updateQPPosition(QPPosition qpPosition) {
		return sqlSession.update("qpPosition.updateQP", qpPosition)>0;
	}

	@Override
	public boolean deleteQPPosition(int qpId) {
		return sqlSession.delete("qpPosition.deleteQP", qpId)>0;
	}

	@Override
	public QPPosition selectQPPosition(int qpId) {
		return sqlSession.selectOne("qpPosition.selectQPbyId", qpId);
	}

	@Override
	public List<QPPosition> selectQPPositionList() {
		return sqlSession.selectList("qpPosition.selectQPPositionList");
	}

	@Override
	public List<QPPosition> selectQPbybCode(String bCode) {
		return sqlSession.selectList("qpPosition.selectQPbybCode", bCode);
	}
	
	@Override
	public List<QPPosition> selectQPbyhCode(String hCode) {
		return sqlSession.selectList("qpPosition.selectQPbyhCode", hCode);
	}

	@Override
	public boolean decline(int qpId) {
		return sqlSession.update("qpPosition.decline", qpId)>0;
	}

	@Override
	public boolean accept(int qpId) {
		return sqlSession.update("qpPosition.accept", qpId)>0;
	}

}
