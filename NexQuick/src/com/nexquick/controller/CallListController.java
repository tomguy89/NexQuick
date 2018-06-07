package com.nexquick.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;
import com.nexquick.service.call.CallSelectListService;

@RequestMapping("/list")
@Controller
public class CallListController {

	private CallSelectListService callSelectListService;
	@Autowired
	public void setCallSelectListService(CallSelectListService callSelectListService) {
		this.callSelectListService = callSelectListService;
	}
	
	/**
	 * 현재 진행중인 오더 목록(배송 완료 전) 가져오기
	 * @param session
	 * @return OnDelivery List
	 */
	@RequestMapping("/userCallList.do")
	public String onDeliveryList(HttpSession session) {
		CSInfo csInfo = (CSInfo)session.getAttribute("csInfo");
		List<OnDelivery> list = callSelectListService.onDeliveryCallList(csInfo.getCsId());
		session.setAttribute("userCallList", list);
		return "mainPage/main_list";
	}
	
	@RequestMapping("/userCallLists.do")
	public @ResponseBody List<OnDelivery> onDeliveryLists(HttpSession session) {
		CSInfo csInfo = (CSInfo)session.getAttribute("csInfo");
		return callSelectListService.onDeliveryCallList(csInfo.getCsId());
	}
	
	@RequestMapping("/userAllCallList.do")
	public String AllCallList(HttpSession session, String csId) {
		List<OnDelivery> list = callSelectListService.getAllOrderByCsId(csId);
		session.setAttribute("userAllCallList", list);
		return "csPage/UserPage"; 
	}
	
	@RequestMapping("/userAllCallLists.do")
	public @ResponseBody List<OnDelivery> AllCallLists(HttpSession session, String csId) {
		return callSelectListService.getAllOrderByCsId(csId); 
	}
	
	
	@RequestMapping("/adminAllOrder.do")
	public String AllCallListForAdmin(HttpSession session) {
		List<OrderInfo> list = callSelectListService.getAllOrderForAdmin();
		session.setAttribute("AllOrderForAdmin", list);
		return "admin/admin_OrderPage"; 
	}
	
	@RequestMapping("/confirmCall.do")
	public @ResponseBody List<CallInfo> selectCallList(String csId, String qpId) {
		System.out.println(qpId);
		System.out.println(csId);
		return callSelectListService.selectCallList(csId, Integer.parseInt(qpId), 2);
	}
	
	@RequestMapping("/confirmOrder.do")
	public @ResponseBody OrderInfo selectOrderListToConfrim(String qpId, String receiverPhone) {
		return (OrderInfo) callSelectListService.selectOrderListToConfrim(qpId,receiverPhone);
	}
		
	
}
