package com.nexquick.service.parsing;

import com.nexquick.model.vo.Address;

/**
 * 좌표, 도로명/지번 주소 등으로 주소 정보를 조회하여 주소 객체로 만드는 서비스
 * @author Team Balbadak
 * @
 */
public interface AddressTransService {

	/**
	 * 도로명, 지번 주소로 조회하여 주소 정보 리턴
	 * @param addr:도로명/지번 주소
	 * @return Address
	 */
	Address getAddress(String addr);

	/**
	 * 좌표값으로 조회하여 주소 정보 리턴
	 * @param longitude:경도
	 * @param latitude:위도
	 * @return Address
	 */
	Address getAddress(double longitude, double latitude);

}