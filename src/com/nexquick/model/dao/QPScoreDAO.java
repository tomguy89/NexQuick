package com.nexquick.model.dao;

import java.util.List;

import com.nexquick.model.vo.QPScore;

/**
 * QuickPro의 별점을 관리하는 DB에 접근하는 DAO
 * @author Team Balbadak
 *
 */
public interface QPScoreDAO {
	
	/**
	 * 콜/오더 완료 시 해당 콜/오더에 대한 평점을 추가
	 * @param qpScore
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createScore(QPScore qpScore);
	
	/**
	 * 주문자의 콜 번호와 매칭되는 평점 정보
	 * @param callNum
	 * @return QPScore
	 */
	QPScore scoreByCallNum(int callNum);
	
	/**
	 * 받은 사람의 오더 번호와 매칭되는 평점 정보
	 * @param orderNum
	 * @return QPScore
	 */
	QPScore scoreByOrderNum(int orderNum);
	
	/**
	 * QP의 아이디로 모든 평점 정보 조회
	 * @param qpId
	 * @return QPScore List
	 */
	List<QPScore> selectQPScore(int qpId);
}
