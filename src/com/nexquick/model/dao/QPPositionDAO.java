package com.nexquick.model.dao;

import java.util.List;

import com.nexquick.model.vo.QPPosition;

/**
 * 출근한 QuickPro들의 위치 정보를 관리하는 DB에 접근하는 DAO입니다.
 * @author Team Balbadak
 *
 */
public interface QPPositionDAO {
	
	/**
	 * QP 출근시  위치 정보를 추가
	 * @param qpPosition
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createQPPosition(QPPosition qpPosition);
	
	/**
	 * QP 위치 정보를 업데이트
	 * @param qpPosition
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateQPPosition(QPPosition qpPosition);
	
	/**
	 * QP 퇴근 시 레이블 삭제
	 * @param qpId
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean deleteQPPosition(int qpId);
	
	/**
	 * QP ID로 QP의 위치 정보 조회
	 * @param qpId
	 * @return QPPosition
	 */
	QPPosition selectQPPosition(int qpId);
	
	/**
	 * 전체 QP 위치 정보 조회(관리자용)
	 * @return QPPosition List
	 */
	List<QPPosition> selectQPPositionList();
	
	/**
	 * 지역 코드가 일치하는 QP들의 위치 정보 조회
	 * @param localCode
	 * @return QPPosition List
	 */
	List<QPPosition> selectQPPositionList(int localCode);
	
}
