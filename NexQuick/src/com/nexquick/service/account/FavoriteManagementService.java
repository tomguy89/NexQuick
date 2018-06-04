package com.nexquick.service.account;

import java.util.List;

import com.nexquick.model.vo.FavoriteInfo;

/**
 * 고객의 편의를 위한 주소 관리 서비스
 * @author student
 *
 */
public interface FavoriteManagementService {

	/**
	 * 기본 출발지 정보 조회
	 * @param csId
	 * @return AddressInfo (사용자가 지정한 기본 출발지 정보)
	 */
	FavoriteInfo getBasicAddress(String csId);

	/**
	 * 기본 출발지 정보 설정
	 * @param addressInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean setBasicAddress(FavoriteInfo addressInfo);

	/**
	 * 고객이 설정한 즐겨찾는 출발지 목록 조회
	 * @param csId
	 * @return AddressInfo List
	 */
	List<FavoriteInfo> getDepartureList(String csId);

	/**
	 * 고객이 설정한 즐겨찾는 목적지 목록 조회
	 * @param csId
	 * @return AddressInfo List
	 */
	List<FavoriteInfo> getDestinationList(String csId);

}