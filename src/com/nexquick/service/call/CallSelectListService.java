package com.nexquick.service.call;

import java.util.List;

import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;

/**
 * 콜, 오더, 화물 개별/리스트 정보를 제공하는 서비스
 * @author Team balbadak
 *
 */
public interface CallSelectListService {

	/**
	 * 콜 정보로 해당 콜의 오더들을 조회
	 * @param callNum
	 * @return OrderInfo List
	 */
	List<OrderInfo> orderInfoList(int callNum);
	
	/**
	 * 콜 번호로 콜 정보 조회
	 * @param callNum
	 * @return CallInfo
	 */
	CallInfo selectCallInfo(int callNum);

	/**
	 * 고객 아이디로 콜 정보를 조회
	 * @param csId
	 * @return CallInfo (주문 미완료된 1일 이내의 가장 최근 주문) 
	 */
	CallInfo selectCallInfo(String csId);
	
	/**
	 * 오더 번호에 해당하는 오더 정보 조회
	 * @param orderNum
	 * @return OrderInfo
	 */
	OrderInfo selectOrder(int orderNum);

	/**
	 * 배송 진행중인 조회 리스트
	 * @param csId
	 * @return OnDelivery List
	 */
	List<OnDelivery> onDeliveryCallList(String csId);

	FreightInfo selectFreight(int freightNum);
	
	
	
	List<OnDelivery> getAllOrderByCsId(String csId);
	
	List<OrderInfo> getAllOrderForAdmin();
	
}