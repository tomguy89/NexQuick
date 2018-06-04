package com.nexquick.model.dao;

import java.util.List;

import com.nexquick.model.vo.FavoriteInfo;

/**
 * 사용자가 자주 이용하는 주소들의 DB를 접근하는 DAO입니다.
 * @author Team Balbadak
 * 
 */
public interface FavoriteInfoDAO {
	
	/**
	 * 주소 관리 테이블에 주소 추가 
	 * @param addressInfo 
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createAddress(FavoriteInfo addressInfo);
	
	/**
	 * 관리되는 주소의 정보의 변경 
	 * @param addressInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateAddress(FavoriteInfo addressInfo);
	
	/**
	 * 관리되는 주소의 정보 삭제
	 * @param addId
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean deleteAddress(int addId);
	
	/**
	 * 관리되는 주소 개별 조회
	 * @param addId:조회하고자 하는 주소의 ID
	 * @return AddressInfo
	 */
	FavoriteInfo selectAddress(int addId);
	
	/**
	 * 고객 ID에 따른 주소 정보들의 조회
	 * @param csId:고객 아이디
	 * @param addressType:주소의 타입(1.기본출발지 / 2.출발지 / 3.배송지)
	 * @return AddressInfo List(고객 아이디와 주소타입에 맞는 주소 리스트)
	 */
	List<FavoriteInfo> selectAddressList(String csId, int addressType);
}
