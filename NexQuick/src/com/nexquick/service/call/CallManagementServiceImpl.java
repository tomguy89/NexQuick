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
import com.nexquick.model.vo.QPInfo;

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
	public boolean newCallNow(CallInfo callInfo) {
		return callInfoDao.createCallNow(callInfo);
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
	public FreightInfo getFreight(int freightNum) {
		return freightInfoDao.selectFreight(freightNum);
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

	public List<CallInfo> getCallsByCsId(String csId) {
		return callInfoDao.selectCallList(csId);
	}

	@Override
	public List<CallInfo> getAllCallsByIdAndDate(HashMap<String, Object> condition) {
		return callInfoDao.selectCallListByIdAndDate(condition);
	}
	
	@Override
	public List<CallInfo> getAllCallsByNameAndDate(HashMap<String, Object> condition) {
		return callInfoDao.selectCallListByNameAndDate(condition);
	}
	
	@Override
	public QPInfo getQPInfo(int callNum) {
		return callInfoDao.getQPInfo(callNum);
	}
	
//	0615 김민규 추가
	@Override
	public OrderInfo getOrder(int orderNum) {
		return orderInfoDao.selectOrder(orderNum);
	}

	@Override
	public List<OnDelivery> getAllOndeliveryList(HashMap<String, Object> condition) {
		// TODO Auto-generated method stub
		return orderInfoDao.selectAllOndeliveryList(condition);
	}

	@Override
	public List<OnDelivery> getQPCallByIdAndDate(HashMap<String, Object> condition) {
		return orderInfoDao.selectQPCallByIdAndDate(condition);
	}
	
	
}
