package com.nexquick.service.account;

import java.util.HashMap;
import java.util.List;

import com.nexquick.model.vo.CSInfo;

/**
 * 고객들의 계정과 관련된 서비스입니다.
 * @author Team Balbadak
 *
 */
public interface CSAccountService {
	
	/**
	 * 고객 계정 로그인
	 * @param csId:고객 아이디
	 * @param csPassword:고객 비밀번호
	 * @see com.nexquick.model.dao.CSInfoDAO#selectCS(String)
	 * @return 로그인 성공 시:CSInfo / 로그인 실패 시:null
	 */
	CSInfo csSignIn(String csId, String csPassword, String token);
	
	/**
	 * 고객 신규 가입
	 * @param csInfo
	 * @see com.nexquick.model.dao.CSInfoDAO#createCS(CSInfo)
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean csSignUp(CSInfo csInfo);
	
	/**
	 * 아이디 중복 체크(고객 신규 가입 시)
	 * @param csId:입력한 고객 아이디
	 * @see com.nexquick.model.dao.CSInfoDAO#selectCS(String)
	 * @return 중복 시:true / 비 중복 시:false
	 */
	boolean csIdDuplicateCheck(String csId);
	
	/**
	 * 고객 정보 수정
	 * @param csInfo
	 * @see com.nexquick.model.dao.CSInfoDAO#updateCS(CSInfo)
	 * @return 정상처리:true / 비정상처리:false
	 */
	boolean csModify(CSInfo csInfo);
	
	
	List<CSInfo> csAllList();

	List<CSInfo> csAllListByName(HashMap<String, Object> condition);
	
}
