package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPInfo;

/**
 * 고객이 주문한 콜(Call)을 관리하는 DB를 접근하는 DAO입니다.
 * @author Team Balbadak
 *
 */
public interface CallInfoDAO {
	
	/**
	 * 새로 주문받은 콜에 대한 정보 추가
	 * @param callInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createCall(CallInfo callInfo);
	
//	지금 시간대로 콜 생성
	public boolean createCallNow(CallInfo callInfo);

	
	/**
	 * 콜에 대한 정보 수정
	 * @param callInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateCall(CallInfo callInfo);
	
	/**
	 * 콜 취소나 미처리 후 일정 시간이 지나면 삭제(콜 정보 삭제 시 오더 정보 먼저 삭제)
	 * @param callNum:콜 번호
	 * @see com.nexquick.model.dao.OrderInfoDAO#deleteOrder(int)
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean deleteCall(int callNum);
	
	/**
	 * 콜 번호에 해당하는 콜 조회
	 * @param callNum:콜 번호
	 * @return CallInfo
	 */
	CallInfo selectCall(int callNum);
	
	/**
	 * 고객 아이디와 배송진행상태 0인 콜 조회
	 * @param callNum:콜 번호
	 * @return CallInfo
	 */
	CallInfo selectCall(String csId);
	
	/**
	 * 전체 콜 리스트 조회(관리자 전용)
	 * @return CallInfo List(모든 콜 정보)
	 */
	List<CallInfo> selectCallList();
	
	/**
	 * 해당 QP가 받은 콜 리스트(관리자/QP/고객 전용)
	 * csId, qpId, deliveryStatus를 입력(공백 가능)하여 조회
	 * @param csId:고객ID
	 * @param qpId:QPID
	 * @param deliveryStatus:배송진행상태(1.주문완료 / 2.배차완료 / 3.배송중 / 4.배송완료)
	 * @return CallInfo List(각 조건과 매칭되는 콜 리스트)
	 */
	List<CallInfo> selectCallList(String csId, int qpId, int deliveryStatus);

	void deletePastCalls();

	List<CallInfo> selectCallList(String csId);

//	고객용, 날짜로 검색
	List<CallInfo> selectCallListByIdAndDate(HashMap<String, Object> condition);

//	관리자용, 이름과 날짜로 검색
	List<CallInfo> selectCallListByNameAndDate(HashMap<String, Object> condition);
	
	void updateCallAfterConfirm(List<Integer> list);
	void updateAfterOrdersChecked(int callNum);
	
//	배송기사 이름가져오기
	QPInfo getQPInfo(int callNum);
	
	//0612 이은진 추가
	List<CallInfo> selectUnpayedCall(int qpId);
	int selectUnpayedSum(List<Integer> list);
	void updatePayStatus(List<Integer> list);
	
	

	
	//0612 이은진 추가222
	List<CallInfo> onSpotAdvancePayCall(List<CallInfo> list);

	void payComplete(List <Integer> list);
	String findCSIdbyCallNum(int callNum);
	
}
