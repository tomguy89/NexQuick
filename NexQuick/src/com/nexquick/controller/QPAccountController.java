package com.nexquick.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public @ResponseBody boolean csSignIn(String qpPhone, String qpPassword, HttpSession session) {
		System.out.println(qpPhone+qpPassword);
		QPInfo qpInfo = qpAccountService.qpSignIn(qpPhone, qpPassword);
		if(qpInfo != null) {
			session.setAttribute("qpInfo", qpInfo);
			return true;
		}
		else return false;
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
	
	
	
}
