package com.nexquick.service.account;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.QPInfo;

/**
 * QuickPro들의 계정과 관련된 서비스입니다.
 * @author Team Balbadak
 *
 */
public interface QPAccountService {

	/**
	 * QP 계정 로그인
	 * @param qpPhone
	 * @param qpPassword
	 * @see com.nexquick.model.dao.QPInfoDAO#selectQP(String)
	 * @return 로그인 성공 시:QPInfo / 로그인 실패 시:null
	 */
	QPInfo qpSignIn(String qpPhone, String qpPassword);
	
	/**
	 * QP 신규 가입
	 * @param qpInfo
	 * @see com.nexquick.model.dao.QPInfoDAO#createQP(QPInfo)
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean qpSignUp(QPInfo qpInfo);
	
	/**
	 * QP 전화번호 중복 체크
	 * @param qpPhone
	 * @see com.nexquick.model.dao.QPInfoDAO#selectQP(String)
	 * @return 중복 시:true / 비 중복 시:false
	 */
	boolean qpPhoneDuplicateCheck(String qpPhone);
	
	/**
	 * QP 정보 수정
	 * @param qpInfo
	 * @see com.nexquick.model.dao.QPInfoDAO#updateQP(QPInfo)
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean qpModify(QPInfo qpInfo);
	
	List<QPInfo> qpAllList();
	
//	0615 김민규 수정(qpAllListName을 수정함)
	List<QPInfo> qpAllListSearch(HashMap<String, Object> condition);
	
	//0612 이은진 추가
	
	QPInfo selectQPAccountById(int qpId);

	QPInfo getQPByCallNum(int callNum);
	
	//0614이은진 추가
	void updateProfile(String qpProfile, String qpPhone);
	
	void updateLicense(String qpLicense, String qpPhone);


}
