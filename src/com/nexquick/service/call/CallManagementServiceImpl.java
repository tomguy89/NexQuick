package com.nexquick.service.call;

import com.nexquick.model.dao.CallInfoDAO;
import com.nexquick.model.dao.FreightInfoDAO;
import com.nexquick.model.dao.OrderInfoDAO;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OrderInfo;

public class CallManagementServiceImpl implements CallManagementService {

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
	public boolean newCall(CallInfo callInfo) {
		return callInfoDao.createCall(callInfo);
	}

	@Override
	public boolean addOrder(OrderInfo orderInfo) {
		return orderInfoDao.createOrder(orderInfo);
	}
	
	@Override
	public boolean addFreight(FreightInfo freightInfo) {
		return freightInfoDao.createFreight(freightInfo);
	}
	
	@Override
	public boolean updateOrder(OrderInfo orderInfo) {
		return orderInfoDao.updateOrder(orderInfo);
	}

	@Override
	public boolean delFreights(int orderNum) {
		return freightInfoDao.deleteFreights(orderNum);
	}
	
	@Override
	public boolean delFreightsOne(int freightNum) {
		return freightInfoDao.deleteFreight(freightNum);
	}

	@Override
	public boolean delOrder(int orderNum) {
		return orderInfoDao.deleteOrder(orderNum);
	}

	@Override
	public boolean delCall(int callNum) {
		return callInfoDao.deleteCall(callNum);
	}

	@Override
	public boolean updateCall(CallInfo callInfo) {
		return callInfoDao.updateCall(callInfo);
	}
	
	@Override
	public void delPastCall() {
		freightInfoDao.deletePastFreights();
		orderInfoDao.deletePastOrders();
		callInfoDao.deletePastCalls();
	}


}
