package com.nexquick.service.call;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;

/**
 * 고객 주문을 접수 및 관리하는 서비스 입니다.
 * @author Team Balbadak
 *
 */
public interface CallManagementService {
	
	/**
	 * 새로운 콜을 생성
	 * @param callInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean newCall(CallInfo callInfo);

//	지금 시간대로 생성
	public boolean newCallNow(CallInfo callInfo);
	
	/**
	 * 콜에 새로운 주문을 추가
	 * @param orderInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean addOrder(OrderInfo orderInfo);

	/**
	 * 오더에 새로운 화물을 추가
	 * @param freightInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean addFreight(FreightInfo freightInfo);
	
	/**
	 * 주문 정보를 수정
	 * @param orderInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateOrder(OrderInfo orderInfo);
	
	/**
	 * 화물 정보를 삭제
	 * @param orderNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean delFreights(int orderNum);

	/**
	 * 화물 정보를 삭제
	 * @param freightNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean delFreightsOne(int freightNum);
	
	/**
	 * 주문 정보를 삭제(오더 삭제 전 화물 정보 삭제해야함)
	 * @param orderNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean delOrder(int orderNum);
	
	/**
	 * 콜 정보를 삭제(콜 삭제 전 먼저 오더를 삭제해야함)
	 * @param callNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean delCall(int callNum);
	
	/**
	 * 콜 정보 업데이트 시 사용
	 * @param callInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateCall(CallInfo callInfo);
	

	/**
	 * 하루 이상 지난 미완료 콜을 삭제
	 */
	void delPastCall();
	
	List<CallInfo> getCallsByCsId(String csId);
	
//	고객용, 날짜로 검색
	List<CallInfo> getAllCallsByIdAndDate(HashMap<String, Object> condition);
//	관리자용, 이름과 날짜로 검색
	List<CallInfo> getAllCallsByNameAndDate(HashMap<String, Object> condition);
}
