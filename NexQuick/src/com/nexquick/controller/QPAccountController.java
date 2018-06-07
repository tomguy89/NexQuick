package com.nexquick.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.QPInfo;
import com.nexquick.service.account.QPAccountService;

@RequestMapping("qpAccount")
@Controller
public class QPAccountController {
	
	private QPAccountService qpAccountService;
	@Autowired
	public void setQpAccountService(QPAccountService qpAccountService) {
		this.qpAccountService = qpAccountService;
	}
	
	
	/**
	 * QP의 로그인을 처리
	 * contextPath/qpAccount/qpSignIn.do
	 * @param qpId:고객아이디
	 * @param qpPassword:비밀번호
	 * @param session:세션에 qpInfo 저장
	 * @return JSON ([비동기 통신] 로그인 성공:true / 로그인 실패:false) 
	 */
	@RequestMapping("/qpSignIn.do")
	public @ResponseBody QPInfo qpSignIn(String qpPhone, String qpPassword, HttpSession session) {
		QPInfo qpInfo = qpAccountService.qpSignIn(qpPhone, qpPassword);
		if(qpInfo != null) {
			session.setAttribute("qpInfo", qpInfo);
			return qpInfo;
		}
		else return null;
	}
	
	/**
	 * 세션에 저장된 로그인 정보를 invalidate 처리
	 * contextPath/qpAccount/signOut.do
	 * @param session
	 * @return view page path (메인 화면)
	 */
	@RequestMapping("/signOut.do")
	public String signOut(HttpSession session) {
		session.invalidate();
		return "";
	}
	
	
	/**
	 * 신규 가입 시 아이디 중복 체크
	 * contextPath/qpAccount/qpPhoneDuplCheck.do
	 * @param qpPhone:qp전화번호
	 * @return JSON (아이디 사용 가능 시:true / 아이디 중복 시:false)
	 */
	@RequestMapping("/qpPhoneDuplCheck.do")
	public @ResponseBody boolean qpPhoneDuplCheck(String qpPhone) {
		return qpAccountService.qpPhoneDuplicateCheck(qpPhone);
	}
	
	
	@RequestMapping("/qpSignUp.do")
	public @ResponseBody boolean qpSignUp(String qpPhone, String qpPassword, String qpName, String qpLicense,
						   int vehicleType, String qpProfile, String qpBank, String qpAccount) {
		
		QPInfo qpInfo = new QPInfo(qpPhone, qpPassword, qpName, "면허", vehicleType, "사진", qpAccount, qpBank);
		
		if(qpAccountService.qpSignUp(qpInfo)) return true;
		else return false;
	}
	
	
}
