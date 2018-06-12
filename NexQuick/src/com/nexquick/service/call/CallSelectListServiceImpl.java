package com.nexquick.service.call;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.dao.CallInfoDAO;
import com.nexquick.model.dao.FreightInfoDAO;
import com.nexquick.model.dao.OrderInfoDAO;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;

public class CallSelectListServiceImpl implements CallSelectListService {
	
	private CallInfoDAO callInfoDao;
	public void setCallInfoDao(CallInfoDAO callInfoDao) {
		this.callInfoDao = callInfoDao;
	}

	private OrderInfoDAO orderInfoDao;
	public void setOrderInfoDao(OrderInfoDAO orderInfoDao) {
		this.orderInfoDao = orderInfoDao;
	}
	
	private FreightInfoDAO freightInfoDao;
	public void setFreightInfoDao(FreightInfoDAO freightInfoDao) {
		this.freightInfoDao = freightInfoDao;
	}

	@Override
	public List<OrderInfo> orderInfoList(int callNum){
		return orderInfoDao.selectOrderList(callNum);
	}

	@Override
	public CallInfo selectCallInfo(int callNum) {
		return callInfoDao.selectCall(callNum);
	}

	@Override
	public CallInfo selectCallInfo(String csId) {
		return callInfoDao.selectCall(csId);
	}
	
	@Override
	public List<OnDelivery> onDeliveryCallList(String csId){
		return orderInfoDao.onDeliveryCallList(csId);
	}
	
	@Override
	public OrderInfo selectOrder(int orderNum) {
		return orderInfoDao.selectOrder(orderNum);
	}

	@Override
	public FreightInfo selectFreight(int freightNum) {
		return freightInfoDao.selectFreight(freightNum);
	}
	
	
	@Override
	public List<OnDelivery> getAllOrderByCsId(String csId) {
		return orderInfoDao.selectAllOrderListByCsId(csId);
	}
	

	public List<OrderInfo> getAllOrderForAdmin() {
		return orderInfoDao.selectOrderList();
	}
	
	@Override
	public List<CallInfo> selectCallList(String csId, int qpId, int deliveryStatus) {
		return callInfoDao.selectCallList(csId, qpId, deliveryStatus);
	}

	@Override
	public List<OrderInfo> selectOrderListToConfrim(String qpId, String receiverPhone) {
		return orderInfoDao.selectOrderListToConfrim(qpId,receiverPhone);
	}
	
	@Override
	public void updateCallAfterConfirm(List<Integer> list) {
		callInfoDao.updateCallAfterConfirm(list);
		
	}

	@Override
	public void updateOrderAfterConfirm(List<Integer> list) {
		orderInfoDao.updateOrderAfterConfirm(list);
		
	}

	@Override
	public int sumIsGet(int callNum) {
		return orderInfoDao.sumIsGet(callNum);
	}

	@Override
	public int countLinkedOrder(int callNum) {
		return orderInfoDao.countLinkedOrder(callNum);
	}

	@Override
	public void updateAfterOrdersChecked(int callNum) {
		callInfoDao.updateAfterOrdersChecked(callNum);
		
	}

	@Override
	public List<OnDelivery> orderListByQPId(int qpId) {
		return orderInfoDao.orderListByQPId(qpId);
	}
	
	//0612 이은진 추가.
	@Override
	public List<CallInfo> selectUnpayedCall(int qpId) {
		// TODO Auto-generated method stub
		return callInfoDao.selectUnpayedCall(qpId);
	}

	@Override
	public int selectUnpayedSum(List<Integer> list) {
		// TODO Auto-generated method stub
		return callInfoDao.selectUnpayedSum(list);
	}

	@Override
	public void updatePayStatus(List<Integer> list) {
		callInfoDao.updatePayStatus(list);
	}
	
	
}
