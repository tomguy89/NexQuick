package com.nexquick.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.service.account.CSAccountService;
import com.nexquick.service.call.CallSelectListService;

/**
 * 고객의 계정을 관리하는 컨트롤러 입니다.
 * @author Team Balbadak
 *
 */
@RequestMapping("/account")
@Controller
public class CSAccountController {
	
	private CSAccountService csAccountService;
	@Autowired
	public void setCsAccountService(CSAccountService csAccountService) {
		this.csAccountService = csAccountService;
	}
	
	private CallSelectListService callSelectListService;
	@Autowired
	public void setCallSelectListService(CallSelectListService callSelectListService) {
		this.callSelectListService = callSelectListService;
	}

	/**
	 * 고객의 로그인을 처리
	 * contextPath/account/csSignIn.do
	 * @param csId:고객아이디
	 * @param csPassword:비밀번호
	 * @param session:세션에 csInfo 저장
	 * @return JSON ([비동기 통신] 로그인 성공:true / 로그인 실패:false) 
	 */
	@RequestMapping("/csSignIn.do")
	public @ResponseBody CSInfo csSignIn(String csId, String csPassword, String token, HttpSession session) {
		CSInfo csInfo = csAccountService.csSignIn(csId, csPassword, token);
		if(csInfo != null) {
			session.setAttribute("csInfo", csInfo);
			CallInfo inComplCall = callSelectListService.selectCallInfo(csInfo.getCsId());
			if(inComplCall!=null) {
				session.setAttribute("callNum", inComplCall.getCallNum());
				session.setAttribute("totalPrice", inComplCall.getTotalPrice());
			}
			return csInfo;
		}
		else return null;
	}
	
	/**
	 * 세션에 저장된 로그인 정보를 invalidate 처리
	 * contextPath/account/signOut.do
	 * @param session
	 * @return view page path (메인 화면)
	 */
	@RequestMapping("/signOut.do")
	public @ResponseBody boolean signOut(HttpSession session) {
		session.invalidate();
		return true;
	}
	
	@RequestMapping("/signAppOut.do")
	public @ResponseBody boolean signOut(String csId, String token) {
		if(token!=null && token.trim().length() != 0) csAccountService.deleteDeviceInfo(csId);
		return true;
	}
	
	
	/**
	 * 신규 고객 가입 처리
	 * contextPath/account/csSignUp.do
	 * @param csId:고객 아이디
	 * @param csPassword:비밀번호
	 * @param csName:고객 이름
	 * @param csPhone:고객 전화번호
	 * @param csType:고객 유형(1.기업고객 / 2.자영업 / 3.개인 고객)
	 * @param csBusinessName:사업장명(csType 3인 경우 null)
	 * @param csBusinessNumber:사업자 번호(csType 3인 경우 null)
	 * @param csDepartment:부서번호(csType 2,3인 경우 null)
	 * @return view page path (동기 통신 / 가입 완료 후 로그인 화면)
	 */
//	0615 csGrade 추가
	@RequestMapping("/csSignUp.do")
	public @ResponseBody boolean csSignUp(String csId, String csPassword, String csName, String csPhone,
						   int csType, String csBusinessName, String csBusinessNumber, String csDepartment, int csGrade) {
		CSInfo csInfo = new CSInfo(csId, csPassword, csName, csPhone, csType, csBusinessName, csBusinessNumber, csDepartment, csGrade);
		if(csAccountService.csSignUp(csInfo)) return true;
		else return false;
	}
	
	/**
	 * 신규 가입 시 아이디 중복 체크
	 * contextPath/account/csIdDuplCheck.do
	 * @param csId:고객아이디
	 * @return JSON (아이디 사용 가능 시:true / 아이디 중복 시:false)
	 */
	@RequestMapping("/csIdDuplCheck.do")
	public @ResponseBody boolean csIdDuplCheck(String csId) {
		return csAccountService.csIdDuplicateCheck(csId);
	}
	
	/**
	 * 회원 정보를 수정
	 * contextPath/account/csModify.do
	 * @param csPassword:비밀번호
	 * @param csPhone:고객 전화번호
	 * @param csType:고객 유형(1.기업고객 / 2.자영업 / 3.개인 고객)
	 * @param csBusinessName:사업장명(csType 3인 경우 null)
	 * @param csBusinessNumber:사업자 번호(csType 3인 경우 null)
	 * @param csDepartment:부서번호(csType 2,3인 경우 null)
	 * @param session
	 * @return view page path (비동기 통신 / 수정 완료 후 성공/실패 화면)
	 */
	@RequestMapping("/csModify.do")
	public @ResponseBody boolean csInfoModify(String csPassword, String csPhone, int csType, 
							   String csBusinessName, String csBusinessNumber, String csDepartment,
							   HttpSession session) {
		
		CSInfo csInfo = (CSInfo)session.getAttribute("csInfo");
		
		csInfo.setCsPassword(csPassword);
		csInfo.setCsPhone(csPhone);
		csInfo.setCsType(csType);
		if(csBusinessName==null || csBusinessName.trim().length()==0) csInfo.setCsBusinessName(null);
		if(csBusinessNumber==null || csBusinessNumber.trim().length()==0) csInfo.setCsBusinessNumber(null);
		if(csDepartment==null || csDepartment.trim().length()==0) csInfo.setCsDepartment(null);
		
		if(csAccountService.csModify(csInfo)) return true;
		else return false;
	}
	
	@RequestMapping("/sessionCheck.do")
	public @ResponseBody boolean sessionCheck(HttpSession session) {
		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo");
		if(csInfo != null) {
			return true;
		} else {
			return false;
		}
	}
	
//  김민규 0615 추가
	@RequestMapping("/selectBusinessName.do")
	 public @ResponseBody List<String> getBusinessNames(String csBusinessName) {
		System.out.println("컨트롤러");
		List<String> list = csAccountService.getBusinessNames(csBusinessName);
		System.out.println(list.toString());
		return list;
	 }
	
//  김민규 0615 추가
	@RequestMapping("/selectDeptName.do")
	public @ResponseBody List<String> getDepartments(String csBusinessName, String csDepartment) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("csBusinessName", csBusinessName);
		map.put("csDepartment", csDepartment);
		List<String> list = csAccountService.getDepartments(map);
		return list;
	}
	
//	0615 김민규 추가(20:00)
	@RequestMapping("/selectCSByCorporate.do")
	public @ResponseBody List<CSInfo> getCSByCorporate(String csBusinessName, String csDepartment) {
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("csBusinessName", csBusinessName);
		condition.put("csDepartment", csDepartment);
		List<CSInfo> list = csAccountService.getCSByCorporate(condition);
		return list;
	}
	
	
	
	
	
	@RequestMapping("app/getCsInfo.do")
	public @ResponseBody CSInfo getCsInfo(String csId) {
		return csAccountService.getCSInfo(csId);
	}
	
	@RequestMapping("app/csModify.do")
	public @ResponseBody boolean appCsInfoModify(String csId, String csPassword, String csPhone, int csType, 
							   String csBusinessName, String csBusinessNumber, String csDepartment) {
		
		CSInfo csInfo = csAccountService.getCSInfo(csId);
		
		csInfo.setCsPassword(csPassword);
		csInfo.setCsPhone(csPhone);
		csInfo.setCsType(csType);
		if(csBusinessName==null || csBusinessName.trim().length()==0) csInfo.setCsBusinessName(null);
		if(csBusinessNumber==null || csBusinessNumber.trim().length()==0) csInfo.setCsBusinessNumber(null);
		if(csDepartment==null || csDepartment.trim().length()==0) csInfo.setCsDepartment(null);
		
		if(csAccountService.csModify(csInfo)) return true;
		else return false;
	}
	
	
}
