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
		return sqlSession.insert("qpPosition.createQPPosition", qpPosition)>0;
	}

	@Override
	public boolean updateQPPosition(QPPosition qpPosition) {
		return sqlSession.update("qpPosition.updateQPPosition", qpPosition)>0;
	}

	@Override
	public boolean deleteQPPosition(int qpId) {
		return sqlSession.delete("qpPosition.deleteQPPosition", qpId)>0;
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

}
