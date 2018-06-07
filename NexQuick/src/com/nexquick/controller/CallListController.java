package com.nexquick.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
		return callSelectListService.selectCallList(csId, Integer.parseInt(qpId), 2);
	}
	
	@RequestMapping("/confirmOrder.do")
	public @ResponseBody List<OrderInfo> selectOrderListToConfrim(String qpId, String receiverPhone) {
		return callSelectListService.selectOrderListToConfrim(qpId,receiverPhone);
	}
	
	
	@RequestMapping("/updateCallAfterConfirm.do")
	public void updateCallList(JSONArray jsonarray) {	
		List <Integer> list = new ArrayList<>();
		System.out.println("updatecalllist로 들어왔다.");
		/*여기서 리스트를 만든다.
		 */
		for(int i=0;i<jsonarray.size();i++) {
			JSONObject object = (JSONObject) jsonarray.get(i);
			System.out.println("object에서 callNum 키로 뺀 값은"+object.get("callNum"));
			list.add((Integer) object.get("callNum"));
		}
		callSelectListService.updateCallAfterConfirm(list);
	}
	
	
	@RequestMapping("/updateOrderAfterConfirm.do")
	public void updateOrderList(JSONArray jsonarray) {
		System.out.println("updateorderlist로 들어왔다.");
		
		List <Integer> list = new ArrayList<>();
		for(int i=0;i<jsonarray.size();i++) {
			JSONObject object = (JSONObject) jsonarray.get(i);
			System.out.println("object에서 orderNum 키로 뺀 값은"+object.get("orderNum"));
			list.add((Integer) object.get("orderNum"));
		}
		
		callSelectListService.updateOrderAfterConfirm(list);
		
		//업데이트  하고 나서 
		
		for(int i=0;i<list.size();i++) {
			JSONObject object = (JSONObject) jsonarray.get(i);
			int orderNum = (Integer)object.get("orderNum");
			int callNum=callSelectListService.selectOrder(orderNum).getCallNum();
			int comp1=callSelectListService.sumIsGet(callNum);
			int comp2=callSelectListService.countLinkedOrder(callNum);
			
			if(comp1==comp2) {
				callSelectListService.updateAfterOrdersChecked(callNum);
			}
		}
		
		//받은 orderNum
	}
	
}
