package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.QPInfo;

/**
 * QuickPro의 정보를 관리하는 DB에 접근하는 DAO입니다.
 * @author Team Balbadak
 *
 */
public interface QPInfoDAO {
	
	/**
	 * 새로운 QP(새로 가입한 QP) 추가
	 * @param qpInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createQP(QPInfo qpInfo);
	void createQPAccount(QPInfo qpInfo);

	/**
	 * QP 정보 업데이트
	 * @param qpInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateQP(QPInfo qpInfo);
	
	/**
	 * QP ID로 QP 정보 조회
	 * @param qpId
	 * @return QPInfo
	 */
	QPInfo selectQP(int qpId);
	
	/**
	 * QP 전화번호로 QP 정보 조회
	 * @param qpPhone
	 * @return QPInfo
	 */
	QPInfo selectQP(String qpPhone);
	
	/**
	 * 전체 QP 정보 조회(관리자용)
	 * @return QPInfo List
	 */
	List<QPInfo> selectQPList();
	
	/**
	 * 차량 종류로 QP 정보 조회
	 * @param qpVehicleType:차량타입 (1.오토바이 / 2.다마스 / 3.라보 / 4.1t트럭)
	 * @return QPInfo List
	 */
	List<QPInfo> selectQPList(int qpVehicleType);
	
//	관리자용. 이름으로검색
	List<QPInfo> selectQPListByName(HashMap<String, Object> condition);


	
}
