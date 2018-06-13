package com.nexquick.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPInfo;
import com.nexquick.model.vo.QPPay;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.QPAccountService;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.call.CallSelectListService;

@RequestMapping("/qpAccount")
@Controller
public class QPAccountController {
	
	private QPAccountService qpAccountService;
	@Autowired
	public void setQpAccountService(QPAccountService qpAccountService) {
		this.qpAccountService = qpAccountService;
	}
	
	private QPPositionService qpPositionService;
	@Autowired
	public void setQpPositionService(QPPositionService qpPositionService) {
		this.qpPositionService = qpPositionService;
	}
	
	private CallSelectListService callSelectListService;
	@Autowired
	public void setCallSelectListService(CallSelectListService callSelectListService) {
		this.callSelectListService = callSelectListService;
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
		
		QPInfo qpInfo = new QPInfo(qpPassword, qpName, qpPhone, "면허", vehicleType, "사진", qpAccount, qpBank);
		
		if(qpAccountService.qpSignUp(qpInfo)) return true;
		else return false;
	}
	
//	새로추가 0608
	@RequestMapping("/getQPPosition.do")
	public @ResponseBody QPPosition getQPPosition(int qpId) {
		return qpPositionService.getQPPosition(qpId);
	}
	
	
	//0612 이은진 추가.
/*	1. qpId로 bank찾아오기
	2. qpId로 unpayedcall갖고오기
	3. unpayedcall의 콜넘으로 총액 갖고오기
	4. unpyedcall의 콜넘으로 결제상태 업데이트하기*/
	
	@RequestMapping("processPayment.do")
	public @ResponseBody QPPay processPayment(int qpId) {
		
		System.out.println("processPayment 컨트롤러에 들어옴");
		
		QPInfo qpInfo = qpAccountService.selectQPAccountById(qpId);
		
		System.out.println(qpInfo.toString());
		
		QPPay qpPay = new QPPay();
		qpPay.setQpBank(qpInfo.getQpBank());
		qpPay.setQpAccount(qpInfo.getQpAccount());
		
		
		
		List<CallInfo> callList = callSelectListService.selectUnpayedCall(qpId);
		int callListSize = callList.size();
		
		if(callListSize>0) {
			List<Integer> list=  new ArrayList<>();
			
			for(int i=0;i<callListSize;i++) {
			list.add(callList.get(i).getCallNum());
			}
		
			int money = callSelectListService.selectUnpayedSum(list);
			qpPay.setMoney(money);
		
			updatePaystatus(list);
		}
		
		
		return qpPay;
		
	}
	
	public void updatePaystatus(List <Integer> list) {
		System.out.println("updatePayStatus 컨트롤러에 들어옴");
		callSelectListService.updatePayStatus(list);
	}
	
	
	
//	0613 새로추가
	@RequestMapping("/getQPByCallNum.do")
	public @ResponseBody QPPosition qpPositionByCallNum(int callNum) {
		QPInfo qpInfo = qpAccountService.getQPByCallNum(callNum);
		int qpId = qpInfo.getQpId();
		return qpPositionService.selectQPPositionByCallNum(qpId);
	}
	
	
	
	
	
}
