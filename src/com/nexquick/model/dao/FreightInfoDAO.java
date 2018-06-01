package com.nexquick.model.dao;

import java.util.List;

import com.nexquick.model.vo.FreightInfo;

/**
 * 오더 정보에 들어가는 화물 정보 DB에 접근하는 DAO입니다.
 * @author Team Balbadak
 *
 */
public interface FreightInfoDAO {
	
	/**
	 * 화물 정보 추가
	 * @param freightInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createFreight(FreightInfo freightInfo);
	
	/**
	 * 화물 정보 수정
	 * @param freightInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateFreight(FreightInfo freightInfo);
	
	/**
	 * 화물 정보 삭제
	 * @param freightNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean deleteFreight(int freightNum);
	
	/**
	 * 오더번호에 해당하는 화물 정보들을 삭제
	 * @param orderNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean deleteFreights(int orderNum);
	
	/**
	 * 화물 번호로 화물 개별 조회
	 * @param freightNum
	 * @return FreightInfo(화물 번호가 매칭되는 화물 정보)
	 */
	FreightInfo selectFreight(int freightNum);
	
	/**
	 * 전체 화물 정보 리스트
	 * @return FreightInfo List
	 */
	List<FreightInfo> selectFreightList();
	
	/**
	 * 오더 정보에 해당하는 전체 화물 리스트
	 * @param orderNum:주문 번호
	 * @return FreightInfo(주문 번호에 해당하는 화물 정보)
	 */
	List<FreightInfo> selectFreightList(int orderNum);

	void deletePastFreights();
}
