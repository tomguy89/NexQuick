package com.nexquick.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

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
}
