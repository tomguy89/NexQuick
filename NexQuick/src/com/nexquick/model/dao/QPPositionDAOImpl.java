package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.Address;
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
	public List<QPPosition> selectQPByBCode(Map<String, Object> param) {
		return sqlSession.selectList("qpPosition.selectQPByBCode", param);
	}
	
	@Override
	public List<QPPosition> selectQPByHCode(Map<String, Object> param) {
		return sqlSession.selectList("qpPosition.selectQPByHCode", param);
	}

	@Override
	public boolean decline(int qpId) {
		return sqlSession.update("qpPosition.decline", qpId)>0;
	}

	@Override
	public boolean accept(int qpId) {
		return sqlSession.update("qpPosition.accept", qpId)>0;
	}

	@Override
	public boolean changeQPStatus(int qpId, int qpStatus) {
		Map<String, Object> map = new HashMap<>(); 
		map.put("qpId", qpId);
		map.put("qpStatus", qpStatus);
		return sqlSession.update("qpPosition.changeQPStatus", map)>0;
	}

}
