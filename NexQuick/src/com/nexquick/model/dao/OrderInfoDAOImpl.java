package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nexquick.model.vo.BusinessOrderInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;

public class OrderInfoDAOImpl implements OrderInfoDAO {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean createOrder(OrderInfo orderInfo) {
		return sqlSession.insert("orderInfo.createOrder", orderInfo)>0;
	}

	@Override
	public boolean updateOrder(OrderInfo orderInfo) {
		return sqlSession.update("orderInfo.updateOrder", orderInfo)>0;
	}

	@Override
	public boolean deleteOrder(int orderNum) {
		return sqlSession.delete("orderInfo.deleteOrder", orderNum)>0;
	}

	@Override
	public OrderInfo selectOrder(int orderNum) {
		return sqlSession.selectOne("orderInfo.selectOrder", orderNum);
	}

	@Override
	public List<OrderInfo> selectOrderList() {
		return sqlSession.selectList("orderInfo.selectOrderList");
	}

	@Override
	public List<OrderInfo> selectOrderList(int callNum) {
		return sqlSession.selectList("orderInfo.selectOrderListByCnum", callNum);
	}

	@Override
	public void deletePastOrders() {
		sqlSession.delete("orderInfo.deletePastOrders");
	}

	@Override
	public List<OnDelivery> onDeliveryCallList(String csId) {
		return sqlSession.selectList("orderInfo.onDeliveryCallList", csId);
	}
	
	@Override
	public List<OnDelivery> finishedCallList(String csId) {
		return sqlSession.selectList("orderInfo.finishedCallList", csId);
	}

	@Override
	public List<OnDelivery> selectAllOrderListByCsId(String csId) {
		return sqlSession.selectList("orderInfo.allCallList", csId);
	}
	
	@Override
	public List<OnDelivery> selectAllOrderListByIdAndName(HashMap<String, Object> condition) {
		return sqlSession.selectList("orderInfo.allCallListByIdAndName", condition);
	}
	
	@Override
	public List<OrderInfo> selectOrderListToConfrim(String qpId, String receiverPhone) {
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("qpId", qpId);
		condition.put("receiverPhone", receiverPhone);
		return sqlSession.selectList("orderInfo.selectOrderListToConfrim", condition);
	}
	
	@Override
	public void updateOrderAfterConfirm(List<Integer> list) {
		sqlSession.update("orderInfo.updateOrderAfterConfirm",list);
	}

	@Override
	public int sumIsGet(int callNum) {
		return sqlSession.selectOne("orderInfo.sumIsGet",callNum);
	}

	@Override
	public int countLinkedOrder(int callNum) {
		return sqlSession.selectOne("orderInfo.countLinkedOrder",callNum);
	}

	@Override
	public List<OnDelivery> orderListByQPId(int qpId) {
		return sqlSession.selectList("orderInfo.orderListByQPId", qpId);
	}
	
	
	//0612이은진 추가2222
	@Override
	public List<CallInfo> onSpotDefferedPayCall(int qpId, int orderNum) {
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("qpId", qpId);
		condition.put("orderNum", orderNum);
		return sqlSession.selectList("orderInfo.onSpotDefferedPayCall", condition);
	}

	@Override
	public List<OnDelivery> orderListByCallNum(int callNum) {
		return sqlSession.selectList("orderInfo.orderListByCallNum", callNum);
	}

//	0614 김민규 추가
	@Override
	public List<OnDelivery> getAllOrderByOrderNumber(int orderNum) {
		return sqlSession.selectList("orderInfo.getFreightListByOrderNum", orderNum);
	}

	@Override
	public OnDelivery getOrderByOrderNum(int orderNum) {
		return sqlSession.selectOne("orderInfo.getOrderByOrderNum", orderNum);
	}
	
	// 0615 김민규 추가
	@Override
	public List<BusinessOrderInfo> getBusinessOrderList(HashMap<String, Object> condition) {
		return sqlSession.selectList("orderInfo.getBusinessOrderList", condition);
	}
	
	//0612 이은진 추가.

	@Override
	public List<OnDelivery> selectUnpayedCall(int qpId) {
		
		return sqlSession.selectList("orderInfo.selectUnpayedCall", qpId);
	}
	
	@Override
	public int selectUnpayedSum(List<Integer> list) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("orderInfo.selectUnpayedSum", list);
	}

	@Override
	public void updatePayStatus(List<Integer> list) {
		// TODO Auto-generated method stub
		sqlSession.update("orderInfo.updatePayStatus", list);
	}

	@Override
	public List<OnDelivery> getOnDeliveryCallListLast(int callNum) {
		return sqlSession.selectList("orderInfo.getOnDeliveryCallListLast", callNum);
	}
	

	@Override
	public List<OnDelivery> selectQPTotalList(int qpId) {
		return sqlSession.selectList("orderInfo.selectQPTotalList",qpId);
	}
}
