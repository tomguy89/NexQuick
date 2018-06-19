package com.nexquick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.BusinessOrderInfo;
import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.Coordinate;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;
import com.nexquick.service.call.CallManagementService;
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
	
	@RequestMapping("/app/userCallList.do")
	public @ResponseBody List<OnDelivery> onDeliveryLists(String csId) {
		return callSelectListService.onDeliveryCallList(csId);
	}
	
	@RequestMapping("/userAllCallList.do")
	public String AllCallList(HttpSession session, String csId) {
		List<OnDelivery> list = callSelectListService.getAllOrderByCsId(csId);
		session.setAttribute("userAllCallList", list);
		return "csPage/UserPage"; 
	}
	
	@RequestMapping("/app/userAllCallList.do")
	public @ResponseBody List<OnDelivery> AllCallLists(String csId) {
		return callSelectListService.getAllOrderByCsId(csId); 
	}
	
	@RequestMapping("/app/finishedCallList.do")
	public @ResponseBody List<OnDelivery> finishedCallList(String csId) {
		return callSelectListService.finishedCallList(csId); 
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
			System.out.println("comp1:"+comp1);
			int comp2=callSelectListService.countLinkedOrder(callNum);
			System.out.println("comp2:"+comp2);
			
			if(comp1==comp2) {//모든 isGet이 1로 변했다면
				callSelectListService.updateAfterOrdersChecked(callNum);
			}
		}
	}
	
	
	//0612 이은진 추가2222
	@RequestMapping("/afterBeamforQPS.do")
	public @ResponseBody List<CallInfo> afterBeamforQPS(int qpId, int callNum){//선불현장결제 콜목록
		
		System.out.println(qpId);
		System.out.println(callNum);
		
		String csId = callSelectListService.findCSIdbyCallNum(callNum); //손님 아이디 찾고
		List<CallInfo> callList = callSelectListService.selectCallList(csId, qpId, 2);//qp와 cs 사이 인수 전인 콜 목록들 뽑고
		List<CallInfo> advancedOnSpotList = callSelectListService.onSpotAdvancePayCall(callList);//그 중 선불현장결제의 콜 목록을 뽑는다.
		
		return advancedOnSpotList;//선불현장결제 콜 리스트를 리턴한다. (그러면 앱에서 n건의 콜에 대해 결제 받았냐는 안내메시지가 뜨고 예를 누르면 밑의 payComlete로...)
	}
	
	@RequestMapping("/afterBeamforQPR.do")
	public @ResponseBody List<CallInfo> afterBeamforQPR(int qpId, int orderNum){//후불현장결제 콜목록
		
		System.out.println(qpId);
		System.out.println(orderNum);
		
		return callSelectListService.onSpotDefferedPayCall(qpId, orderNum);//퀵기사와 수령인 사이의 배송중인, 결제가 완료되지 않은 후불결제건을 리턴한다...
	}
	
	
	@RequestMapping("/payComplete.do")
	public void payComplete(String result) {//결제가 완료된 콜의 리스트가 json 형태로 온다.
		
		List <Integer> list = new ArrayList<>();
	
		JSONArray jsonArray = (JSONArray)JSONValue.parse(result);
		JSONObject object;
		for (int i = 0; i < jsonArray.size(); i++){
			object = (JSONObject) jsonArray.get(i);
			System.out.println("object:"+object);
			list.add(Integer.parseInt(object.get("callNum").toString()));
		}
		
		callSelectListService.payComplete(list);
	}
	
	
	
//	오더넘버로 온딜리버리 가져오기(0614 추가, 물품목록도 추가됨)
	@RequestMapping("/getOrderByOrderNumber.do")
	public @ResponseBody List<OnDelivery> getOrderByOrderNumber(int orderNum) {
		List<OnDelivery> onDeliveryList = callSelectListService.getAllOrderByOrderNumber(orderNum);;
		return onDeliveryList;
	}
	
	@RequestMapping("/getOrderByOrderNum.do")
	public @ResponseBody OnDelivery getOrderByOrderNum(int orderNum) {
		return callSelectListService.getOrderByOrderNum(orderNum);
	}
	
	
//  고객 법인관리자 전용. 0615 김민규 추가
	@RequestMapping("/getBusinessOrder.do")
	public @ResponseBody List<BusinessOrderInfo> getBusinessOrder(HttpSession session, String csName, String callTime,
			String csDepartment, int payType) {

		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo");

		HashMap<String, Object> condition = new HashMap<>();
		if (csName == "" || csName.length() == 0) {
			csName = null;
		}
		if (callTime == "" || callTime.length() == 0) {
			callTime = null;
		}
		if (csDepartment == "" || csDepartment.length() == 0) {
			csDepartment = null;
		}
		condition.put("csBusinessName", csInfo.getCsBusinessName());
		condition.put("csName", csName);
		condition.put("callTime", callTime);
		condition.put("csDepartment", csDepartment);
		condition.put("payType", payType);
		return callSelectListService.getBusinessOrderList(condition);
	}
	
	@RequestMapping("/calculationList.do")
	public @ResponseBody List<OnDelivery> calculationList(int qpId){
		return callSelectListService.selectUnpayedCall(qpId);
	}
	
	//0615 qp가 가진 배송 목록 최적으로 가져오기
	
	@RequestMapping("/optimalRoute.do")
	public @ResponseBody List<OnDelivery> optimalRouteList(int qpId){
		return callSelectListService.getOptimalRoute(qpId);
	}
	

	   
	   
	   @RequestMapping("/updateCallAfterConfirmbySign.do")
	   public void updateCallListbySign(int callNum) {
	   
	       System.out.println(callNum);
	       
	       List <Integer> list = new ArrayList<>();

	       list.add(callNum);
	       System.out.println("값 들어감");
	       for (int j : list) {
	    	   System.out.println(j);
	       }
	       callSelectListService.updateCallAfterConfirm(list);

	   }
	   
	   
		@RequestMapping("/qptotalList.do")
		public @ResponseBody List<OnDelivery> qptotalList(int qpId){
			return callSelectListService.qptotalList(qpId);
		}
	   

}
