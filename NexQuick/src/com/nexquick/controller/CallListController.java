package com.nexquick.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
	public @ResponseBody List<OnDelivery> onDeliveryList(HttpSession session) {
		CSInfo csInfo = (CSInfo)session.getAttribute("csInfo");
		List<OnDelivery> list = callSelectListService.onDeliveryCallList(csInfo.getCsId());
		return list;
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
	public void updateCallList(String params) {	
		List <Integer> list = new ArrayList<>();
		//[{"callNum":"2"},{"callNum":"3"}] 이렇게 들어왔다.
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(params);
			JSONArray jsonArray = (JSONArray) obj;
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject jsonObj=(JSONObject) jsonArray.get(i);
				list.add(Integer.valueOf((String) jsonObj.get("callNum")));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		callSelectListService.updateCallAfterConfirm(list);
	}
	
	
	@RequestMapping("/updateOrderAfterConfirm.do")
	public void updateOrderList(String params) {
		System.out.println(params);
		
		List <Integer> list = new ArrayList<>();

		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(params);
			JSONArray jsonArray = (JSONArray) obj;
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject jsonObj=(JSONObject) jsonArray.get(i);
				list.add(Integer.valueOf((String) jsonObj.get("orderNum")));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		callSelectListService.updateOrderAfterConfirm(list);
		
		//업데이트  하고 나서 일괄배송일 경우 isGet들이 모두 1로 변했는지 체크해서 call의 deliveryStatus를 수정한다.
		
		for(int i=0;i<list.size();i++) {
			int orderNum=list.get(i);
			System.out.println(orderNum);
			int callNum=callSelectListService.selectOrder(orderNum).getCallNum();
			System.out.println(callNum);
			int comp1=callSelectListService.sumIsGet(callNum);
			System.out.println(comp1);
			int comp2=callSelectListService.countLinkedOrder(callNum);
			System.out.println(comp2);
			
			if(comp1==comp2) {//모든 isGet이 1로 변했다면
				callSelectListService.updateAfterOrdersChecked(callNum);
			}
		}
		

	}
	
}
