package com.nexquick.service.call;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.nexquick.model.dao.CallInfoDAO;
import com.nexquick.model.dao.FreightInfoDAO;
import com.nexquick.model.dao.OrderInfoDAO;
import com.nexquick.model.dao.QPPositionDAO;
import com.nexquick.model.vo.BusinessOrderInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.Coordinate;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.parsing.OptimalRouteService;

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
	
	private QPPositionDAO qpPositionDao;
	public void setQpPositionDao(QPPositionDAO qpPositionDao) {
		this.qpPositionDao = qpPositionDao;
	}
	
	private OptimalRouteService optimalRouteService;
	public void setOptimalRouteService(OptimalRouteService optimalRouteService) {
		this.optimalRouteService = optimalRouteService;
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
	public List<OnDelivery> finishedCallList(String csId){
		return orderInfoDao.finishedCallList(csId);
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
	public List<OnDelivery> selectUnpayedCall(int qpId) {
		return orderInfoDao.selectUnpayedCall(qpId);
	}

	@Override
	public int selectUnpayedSum(List<Integer> list) {
		return orderInfoDao.selectUnpayedSum(list);
	}

	@Override
	public void updatePayStatus(List<Integer> list) {
		orderInfoDao.updatePayStatus(list);
	}
	
	//0612 이은진 추가2222
	@Override
	public List<CallInfo> onSpotAdvancePayCall(List<CallInfo> list) {
		// TODO Auto-generated method stub
		return callInfoDao.onSpotAdvancePayCall(list);
	}


	@Override
	public void payComplete(List<Integer> list) {
		callInfoDao.payComplete(list);
		
	}

	@Override
	public String findCSIdbyCallNum(int callNum) {
		return callInfoDao.findCSIdbyCallNum(callNum);
	}

	@Override
	public List<CallInfo> onSpotDefferedPayCall(int qpId, int orderNum) {
		return orderInfoDao.onSpotDefferedPayCall(qpId, orderNum);
	}

	@Override
	public List<OnDelivery> orderListByCallNum(int callNum) {
		return orderInfoDao.orderListByCallNum(callNum);
	}

//	0614 김민규 추가
	@Override
	public List<OnDelivery> getAllOrderByOrderNumber(int orderNum) {
		return orderInfoDao.getAllOrderByOrderNumber(orderNum);
	}

	@Override
	public OnDelivery getOrderByOrderNum(int orderNum) {
		return orderInfoDao.getOrderByOrderNum(orderNum);
	}
	
	
	// 0615 김민규 추가
	@Override
	public List<BusinessOrderInfo> getBusinessOrderList(HashMap<String, Object> condition) {
		return orderInfoDao.getBusinessOrderList(condition);
	}
	
	@Override
	public List<OnDelivery> getOptimalRoute(int qpId){
		List<Coordinate> coordinateList = new ArrayList<>();
		
		QPPosition qpPosition = qpPositionDao.selectQPPosition(qpId);
		coordinateList.add(new Coordinate("Q", qpId, qpPosition.getQpLatitude(), qpPosition.getQpLongitude()));

		List<CallInfo> callList = callInfoDao.selectCallList(null, qpId, 2);
		for(CallInfo ci : callList) {
			coordinateList.add(new Coordinate("C", ci.getCallNum(), ci.getLatitude(), ci.getLongitude()));
		}
		
		callList = callInfoDao.selectCallList(null, qpId, 3);
		for(CallInfo ci : callList) {
			List<OrderInfo> orderList = orderInfoDao.selectOrderList(ci.getCallNum());
			for(OrderInfo oi : orderList) {
				coordinateList.add(new Coordinate("O", oi.getOrderNum(), oi.getLatitude(), oi.getLongitude()));
			}
		}
		
		coordinateList.sort(new Comparator<Coordinate>() {
			@Override
			public int compare(Coordinate o1, Coordinate o2) {
			 	return (int) (Math.sqrt(Math.pow((o1.getLatitude()-qpPosition.getQpLatitude()), 2)+Math.pow((o1.getLongitude()-qpPosition.getQpLongitude()), 2))
			 			- Math.sqrt(Math.pow((o2.getLatitude()-qpPosition.getQpLatitude()), 2)+Math.pow((o2.getLongitude()-qpPosition.getQpLongitude()), 2)));
			}
		});
		
		List<OnDelivery> result = new ArrayList<>();
		coordinateList = optimalRouteService.optimization(coordinateList);
		for(int i=1; i<coordinateList.size(); i++) {
			if(coordinateList.get(i).getType().equals("O")) {
				result.add(getOrderByOrderNum(coordinateList.get(i).getNumber()));
			}else if (coordinateList.get(i).getType().equals("C")) {
				result.add(orderListByCallNum(coordinateList.get(i).getNumber()).get(0));
			}
		}
		
		return result;
	}

}
