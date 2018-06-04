package com.nexquick.service.call;

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
	
	
	
}
