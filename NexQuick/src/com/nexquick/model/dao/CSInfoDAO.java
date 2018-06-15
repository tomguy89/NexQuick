package com.nexquick.model.dao;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.CSDevice;
import com.nexquick.model.vo.CSInfo;

/**
 * 고객의 정보를 관리하는 DB에 접근하는 DAO입니다.
 * @author Team Balbadak
 *
 */
public interface CSInfoDAO {
	
	/**
	 * 새로운 고객(신규가입고객) 정보를 추가
	 * @param csInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean createCS(CSInfo csInfo);
	
	/**
	 * 고객 정보를 업데이트
	 * @param csInfo
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean updateCS(CSInfo csInfo);
	
	/**
	 * 입력한 ID와 일치하는 고객 정보를 조회
	 * @param csId
	 * @return CSInfo(입력한 ID에 해당하는 고객 정보)
	 */
	CSInfo selectCS(String csId);
	
	/**
	 * 입력한 전화번호로 고객 정보를 조회
	 * @param csPhone
	 * @return CSInfo(입력한 전화번호에 해당하는 고객정보)
	 */
	CSInfo selectCSbyPhone(String csPhone);
	
	/**
	 * 전체 고객 정보를 조회(관리자용)
	 * @return CSInfo List
	 */
	List<CSInfo> selectCSList();
	
	/**
	 * 사업자 등록번호가 일치하는 기업(자영업) 고객 조회
	 * @param csBusinessNumber
	 * @return CSInfo List(사업자 등록 번호가 일치하는 고객 정보 리스트)
	 */
	List<CSInfo> selectCSList(String csBusinessNumber);
	
//  0615 김민규 수정
	List<CSInfo> selectCSListBySearch(HashMap<String, Object> condition);
	
	boolean lastSignedInDevice(CSDevice csDevice);
	String selectCSDevice(String csId);

	void deleteDeviceInfo(String csId);

//	0615 김민규추가
	List<String> getBusinessNames(String name);
//	0615 김민규추가
	List<String> getDepartments(HashMap<String, Object> condition);
//	0615 김민규추가
	boolean updateCSGrade(CSInfo csInfo);
}
