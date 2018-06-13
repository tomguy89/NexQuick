package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPInfo;

public class CallInfoDAOImpl implements CallInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createCall(CallInfo callInfo) {
		return sqlSession.insert("callInfo.createCall", callInfo)>0;
	}
	
	@Override
	public boolean createCallNow(CallInfo callInfo) {
		return sqlSession.insert("callInfo.createCallNow", callInfo)>0;
	}

	@Override
	public boolean updateCall(CallInfo callInfo) {
		return sqlSession.update("callInfo.updateCall", callInfo)>0;
	}

	@Override
	public boolean deleteCall(int callNum) {
		return sqlSession.delete("callInfo.deleteCall", callNum)>0;
	}

	@Override
	public CallInfo selectCall(int callNum) {
		return sqlSession.selectOne("callInfo.selectCallByCnum", callNum);
	}
	
	@Override
	public CallInfo selectCall(String csId) {
		return sqlSession.selectOne("callInfo.selectCallByCSId", csId);
	}
	
	@Override
	public List<CallInfo> selectCallList(String csId) {
		return sqlSession.selectList("callInfo.selectCallListByCSId", csId);
	}
	
	@Override
	public List<CallInfo> selectCallListByIdAndDate(HashMap<String, Object> condition) {
		return sqlSession.selectList("callInfo.selectCallListByIdAndDate", condition);
	}

	@Override
	public List<CallInfo> selectCallListByNameAndDate(HashMap<String, Object> condition) {
		return sqlSession.selectList("callInfo.selectCallListByNameAndDate", condition);
	}

	@Override
	public List<CallInfo> selectCallList() {
		return sqlSession.selectList("callInfo.selectCallList");
	}

	@Override
	public List<CallInfo> selectCallList(String csId, int qpId, int deliveryStatus) {
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("csId", csId);
		condition.put("qpId", qpId);
		condition.put("deliveryStatus", deliveryStatus);
		return sqlSession.selectList("callInfo.selectCallListByCondition", condition);
	}

	@Override
	public void deletePastCalls() {
		sqlSession.delete("callInfo.deletePastCalls");
	}

	@Override
	public void updateCallAfterConfirm(List<Integer> list) {
		sqlSession.update("callInfo.updateCallAfterConfirm",list);
		
	}

	@Override
	public void updateAfterOrdersChecked(int callNum) {
		sqlSession.update("callInfo.updateAfterOrdersChecked",callNum);
	}
	
	@Override
	public QPInfo getQPInfo(int callNum) {
		return sqlSession.selectOne("callInfo.getQPInfo", callNum);
	}
	
	
	//0612 이은진 추가.
	@Override
	public List<CallInfo> selectUnpayedCall(int qpId) {

		return sqlSession.selectList("callInfo.selectUnpayedCall", qpId);
	}

	@Override
	public int selectUnpayedSum(List<Integer> list) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("callInfo.selectUnpayedSum", list);
	}

	@Override
	public void updatePayStatus(List<Integer> list) {
		// TODO Auto-generated method stub
		sqlSession.update("callInfo.updatePayStatus", list);
	}
	
	
	
	
	//0612 이은진 추가 2222
	
	@Override
	public List<CallInfo> onSpotAdvancePayCall(List<CallInfo> list) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("callInfo.onSpotAdvancePayCall", list);
	}



	@Override
	public void payComplete(List<Integer> list) {
		// TODO Auto-generated method stub
		sqlSession.update("callInfo.payComplete", list);
	}

	@Override
	public String findCSIdbyCallNum(int callNum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("callInfo.findCSIdbyCallNum", callNum);
	}
	
	
	
}
