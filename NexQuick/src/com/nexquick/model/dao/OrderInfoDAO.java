package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.BusinessOrderInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;

/**
 * 콜 정보에 들어가는 오더 정보 DB에 접근하는 DAO입니다.
 * @author Team Balbadak
 *
 */
public interface OrderInfoDAO {
	
	/**
	 * 오더 정보 추가
	 * @param orderInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createOrder(OrderInfo orderInfo);
	
	/**
	 * 오더 정보 수정
	 * @param orderInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateOrder(OrderInfo orderInfo);
	
	/**
	 * 오더 정보 삭제(오더 정보 삭제 시 화물 정보 먼저 삭제)
	 * @param orderNum
	 * @see com.nexquick.model.dao.FreightInfoDAO#deleteFreight(int)
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean deleteOrder(int orderNum);
	
	/**
	 * 오더 번호로 오더 정보 조회
	 * @param orderNum
	 * @return OrderInfo(입력된 오더번호에 매칭되는 오더 정보)
	 */
	OrderInfo selectOrder(int orderNum);
	
	/**
	 * 전체 오더 정보 조회(관리자용)
	 * @return OrderInfo List
	 */
	List<OrderInfo> selectOrderList();
	
	/**
	 * 콜 번호에 포함되는 모든 주문정보 조회
	 * @param callNum
	 * @return OrderInfo List(해당 콜 번호에 포함되는 모든 주문 리스트)
	 */
	List<OrderInfo> selectOrderList(int callNum);

	void deletePastOrders();

	List<OnDelivery> onDeliveryCallList(String csId);
	
	List<OnDelivery> selectAllOrderListByCsId(String csId);
	List<OnDelivery> selectAllOrderListByIdAndName(HashMap<String, Object> condition);

	List<OrderInfo> selectOrderListToConfrim(String qpId, String receiverPhone);
	
	void updateOrderAfterConfirm(List<Integer> list);
	int sumIsGet(int callNum);
	int countLinkedOrder(int callNum);
	List<OnDelivery> orderListByQPId(int qpId);
	
	//0612이은진 추가222
	List<CallInfo> onSpotDefferedPayCall(int qpId, int orderNum);
	List<OnDelivery> orderListByCallNum(int callNum);

//	0614 김민규추가
	List<OnDelivery> getAllOrderByOrderNumber(int orderNum);

	List<OnDelivery> finishedCallList(String csId);
	
	OnDelivery getOrderByOrderNum(int orderNum);

	List<BusinessOrderInfo> getBusinessOrderList(HashMap<String, Object> condition);
	
	
	List<OnDelivery> selectUnpayedCall(int qpId);
	int selectUnpayedSum(List<Integer> list);
	void updatePayStatus(List<Integer> list);

	List<OnDelivery> getOnDeliveryCallListLast(int callNum);
	List<OnDelivery> selectQPTotalList(int qpId);
	
	List<OnDelivery> selectAllOndeliveryList(HashMap<String, Object> condition);
}
