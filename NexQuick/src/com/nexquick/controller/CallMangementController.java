package com.nexquick.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.FavoriteInfo;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OrderInfo;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.FavoriteManagementService;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.call.CallManagementService;
import com.nexquick.service.call.CallSelectListService;
import com.nexquick.service.call.PricingService;
import com.nexquick.service.parsing.AddressTransService;
import com.nexquick.service.parsing.DistanceCheckService;

/**
 * 콜 신청과 관련된 컨트롤러 입니다.
 * @author Team Balbadak
 *
 */
@RequestMapping("/call")
@Controller
public class CallMangementController {

	
	private FavoriteManagementService favoriteManagementService;
	
	@Autowired
	public void setFavoriteManagementService(FavoriteManagementService favoriteManagementService) {
		this.favoriteManagementService = favoriteManagementService;
	}

	private CallManagementService callManagementService;
	@Autowired
	public void setCallManagementService(CallManagementService callManagementService) {
		this.callManagementService = callManagementService;
	}
	
	private CallSelectListService callSelectListService;
	@Autowired
	public void setCallSelectListService(CallSelectListService callSelectListService) {
		this.callSelectListService = callSelectListService;
	}

	private PricingService pricingService;
	@Autowired
	public void setPricingService(PricingService pricingService) {
		this.pricingService = pricingService;
	}
	
	private AddressTransService addressTransService;
	@Autowired
	public void setAddressTransService(AddressTransService addressTransService) {
		this.addressTransService = addressTransService;
	}
	
	private DistanceCheckService distanceCheckService;
	@Autowired
	public void setDistanceCheckService(DistanceCheckService distanceCheckService) {
		this.distanceCheckService = distanceCheckService;
	}
	
	private QPPositionService qpPositionService;
	@Autowired
	public void setQpPositionService(QPPositionService qpPositionService) {
		this.qpPositionService = qpPositionService;
	}


	/**
	 * 고객의 새로운 콜 접수
	 * contextPath/call/newCall.do
	 * @param session
	 * @param senderName:보내는 사람 이름(기본:로그인 된 고객정보 이름)
	 * @param senderAddress:보내는 사람 주소(기본:고객이 설정한 기본 주소)
	 * @param senderPhone:보내는 사람 번호(기본:로그인 된 고객정보 전화번호)
	 * @param vehicleType:운송수단(1.오토바이 / 2.다마스 / 3.라보 / 4.1t트럭)
	 * @param urgent:긴급배송 여부
	 * @param reserved:예약배송 여부
	 * @param reservationTime:인수예정시간
	 * @return JSON (비동기 CallInfo)
	 */
	@RequestMapping("/newCall.do")
	public @ResponseBody CallInfo newCall(HttpSession session, String senderName, String senderAddress, String senderAddressDetail, String senderPhone,
						  int vehicleType, int urgent, int reserved, int series, String reservationTime) {
		//세션에서 고객 아이디 가져오기
		String csId = ((CSInfo)session.getAttribute("csInfo")).getCsId();
		System.out.println(reservationTime);
		//퀵 신청하기에서 주문자 정보 입력
		CallInfo callInfo = new CallInfo(csId, senderName, senderAddress, senderAddressDetail, senderPhone, vehicleType, urgent, reserved, series, reservationTime);
		if(reserved == 0) {
			reservationTime = "";
			callInfo.setReservationTime(reservationTime);
			callManagementService.newCallNow(callInfo);
		} else {
			callManagementService.newCall(callInfo);
		}
		

		//새로 생성된 콜 주문번호 세션에 저장
		int callNum = callInfo.getCallNum();
		session.setAttribute("senderAddress", senderAddress);
		session.setAttribute("callNum", callNum);
		session.setAttribute("totalPrice", 0);
		return callInfo;
	}
	
	
	/**
	 * 콜에 포함되는 오더 정보 추가하기(DB에 오더정보 추가, 세션에 있던 화물 정보도 DB에 추가)
	 * contextPath/call/addOrder.do
	 * @param session
	 * @param receiverName:받는사람 이름
	 * @param receiverAddress:받는사람 주소
	 * @param receiverPhone:받는사람 전화번호
	 * @param memo:배송메시지
	 * @return JSON (비동기 OrderInfo)
	 */
	@RequestMapping("/addOrder.do")
	public @ResponseBody OrderInfo addOrder(HttpSession session,
				String receiverName, String receiverAddress, String receiverPhone, String memo) {
		
		//세션에서 현재 받고있는 콜 넘버 가져오기
		int callNum = (int)session.getAttribute("callNum");
		int totalPrice = (int)session.getAttribute("totalPrice");
		//입력 받은 주문 정보 생성
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		Address sendAddr = addressTransService.getAddress(callInfo.getSenderAddress());
		Address recvAddr = addressTransService.getAddress(receiverAddress);
		Map<String, Object> point = new LinkedHashMap<>();
		point.put("startX", sendAddr.getLongitude());
		point.put("startY", sendAddr.getLatitude());
		point.put("endX", recvAddr.getLongitude());
		point.put("endY", recvAddr.getLatitude());
		double distance = distanceCheckService.singleDistanceCheck(point);
		int price = pricingService.proportionalPrice(distance);
		OrderInfo orderInfo = new OrderInfo(callNum, receiverName, receiverAddress, receiverPhone, memo, price);
		orderInfo.setDistance(distance+"");
		totalPrice += price;
		session.setAttribute("totalPrice", totalPrice);
		callManagementService.addOrder(orderInfo);
		
		return orderInfo;
	}
	
	
	/**
	 * 각 오더에 포함될 화물의 정보 추가(세션에 담고, DB에는 오더가 추가될 때 함께 추가)
	 * contextPath/call/addFreight.do
	 * @param session
	 * @param freightType:추가할 화물 타입
	 * @param freightQuant:화물의 수량
	 * @param freightDetail:화물이 범주를 벗어날 시, 화물에 대한 설명
	 * @return JSON (추가 된 FreightInfo)
	 */
	@RequestMapping("/addFreight.do")
	public @ResponseBody FreightInfo addFreight(HttpSession session, int orderNum, int freightType, int freightQuant, String freightDetail){
		int totalPrice = (int) session.getAttribute("totalPrice");
		//빈칸 처리
		if(freightDetail == null || freightDetail.trim().length()==0) freightDetail = null;
		int price = pricingService.setFreightPrice(freightType, freightQuant);
		totalPrice += price;
		FreightInfo freightInfo = new FreightInfo(orderNum, freightType, freightQuant, price, freightDetail);
		if(callManagementService.addFreight(freightInfo)) {
			session.setAttribute("totalPrice", totalPrice);
			OrderInfo orderInfo = callSelectListService.selectOrder(orderNum);
			orderInfo.setOrderPrice(orderInfo.getOrderPrice()+price);
			callManagementService.updateOrder(orderInfo);
			return freightInfo;
		}else {
			return null;
		}
	}
	
	/**
	 * 화물정보 삭제
	 * contextPath/call/delFreight.do
	 * @param session
	 * @param num:화물이 담긴 번호(map에서 key값)
	 * @return JSON (정상처리:true / 비정상처리:false)
	 */
	@RequestMapping("/delFreight.do")
	public @ResponseBody boolean delFreight(HttpSession session, int freightNum){
		System.out.println(freightNum);
		FreightInfo freightInfo = callSelectListService.selectFreight(freightNum);
		OrderInfo orderInfo = callSelectListService.selectOrder(freightInfo.getOrderNum());
		orderInfo.setOrderPrice(orderInfo.getOrderPrice()-freightInfo.getFreightPrice());
		session.setAttribute("totalPrice", ((int)session.getAttribute("totalPrice"))
				-callSelectListService.selectFreight(freightNum).getFreightPrice());
		return callManagementService.delFreights(freightNum);
	}
	
	
	
	/**
	 * DB에 저장된 주문정보를 삭제
	 * contextPath/call/delOrder.do
	 * @param orderNum:삭제할 주문 번호
	 * @return JSON (정상처리:true / 비정상처리:false)
	 */
	@RequestMapping("/delOrder.do")
	public @ResponseBody boolean delOrder(HttpSession session, int orderNum) {
		return delOrderProcess(session, orderNum);
	}
	
	
	/**
	 * 콜 취소 시, DB에 저장해놨던 임시 정보들(콜, 오더, 화물) 삭제
	 * contextPath/call/cancelCall.do
	 * @param session
	 */
	@RequestMapping("/cancelCall.do")
	public void cancelCall(HttpSession session) {
		int callNum = (int)session.getAttribute("callNum");
		List<OrderInfo> orderInfoList = callSelectListService.orderInfoList(callNum);
		for(OrderInfo oi : orderInfoList) {
			delOrderProcess(session, oi.getOrderNum());
		}
		callManagementService.delCall(callNum);
		session.removeAttribute("callNum");
		session.removeAttribute("totalPrice");
	}
	
	
	/**
	 * 지금까지 받은 콜 정보를 DB에 저장
	 * contextPath/call/registCall.do
	 * @param session
	 * @param totalPrice
	 * @param payType
	 * @param payStatus
	 */
	@RequestMapping("/registCall.do")
	public @ResponseBody boolean registCall(HttpSession session, int payType, int payStatus) {
		int callNum = (int)session.getAttribute("callNum");
		int totalPrice = (int)session.getAttribute("totalPrice");
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		callInfo.setDeliveryStatus(1);
		callInfo.setTotalPrice(totalPrice);
		callInfo.setPayType(payType);
		callInfo.setPayStatus(payStatus);
		session.removeAttribute("callNum");
		callManagementService.updateCall(callInfo);
		return true;
	}
	
	@RequestMapping("/updateCall.do")
	public @ResponseBody boolean updateCall(HttpSession session, int callNum, String senderName, String senderAddress, String senderAddressDetail, String senderPhone,
			  int vehicleType, int urgent, int reserved, int series, String reservationTime) {
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		callInfo.setSenderName(senderName);
		callInfo.setReservationTime(reservationTime);
		callInfo.setSenderAddress(senderAddress);
		callInfo.setSenderAddressDetail(senderAddressDetail);
		callInfo.setSenderPhone(senderPhone);
		callInfo.setVehicleType(vehicleType);
		callInfo.setUrgent(urgent);
		callInfo.setReserved(reserved);
		callInfo.setSeries(series);
		callManagementService.updateCall(callInfo);
		return true;
	}
	
	
	
	public void allocate() {
		String addr = null;
		List<QPPosition> qpList = null;
		Address address = addressTransService.getAddress(addr);
		if(address.gethCode()!=null || address.gethCode().length() != 0) {
			qpList = qpPositionService.selectQPListByHCode(address.gethCode());
		}else if(address.getbCode()!=null || address.getbCode().length() != 0) {
			qpList = qpPositionService.selectQPListByBCode(address.getbCode());
		}
		
	}
	
	
	
	@RequestMapping("/handover.do")
	public void handOver(int callNum) {
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		callInfo.setDeliveryStatus(3);
		callManagementService.updateCall(callInfo);
	}
	
	
	@RequestMapping("/completeOrder.do")
	public void completeOrder(int orderNum) {
		OrderInfo orderInfo = callSelectListService.selectOrder(orderNum);
		orderInfo.setIsGet(1);
		callManagementService.updateOrder(orderInfo);
	}
	
	

	/**
	 * 하루 이상 지난 결제 안된 미완료 콜 삭제
	 * contextPath/call/delPastCall.do
	 */
	@RequestMapping("/delPastCall.do")
	public void delPastCall() {
		callManagementService.delPastCall();
	}
	
	
	/**
	 * 주문 삭제를 위한 메서드(재사용성을 위해 메서드 분리)
	 * @param session
	 * @param orderNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	public boolean delOrderProcess(HttpSession session, int orderNum) {
		if(callManagementService.delFreights(orderNum)) {
			session.setAttribute("totalPrice", ((int)session.getAttribute("totalPrice"))
							-callSelectListService.selectOrder(orderNum).getOrderPrice());
			return callManagementService.delOrder(orderNum);
		}else {
			return false;
		}
	}
	
//	콜번호로 오더들 조회(마지막페이지)
	@RequestMapping("/getOrders.do")
	public String orderListByCallNum(HttpSession session, int callNum) {
		List<OrderInfo> list = callSelectListService.orderInfoList(callNum);
		session.setAttribute("ordersByCall", list);
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		session.setAttribute("getCallByCallNum", callInfo);
		return "quickApplyPage/quickApply_end";
	}
	
//	콜번호로 오더들 조회(신청조회 페이지)
	@RequestMapping("/getOrderList.do")
	public @ResponseBody List<OrderInfo> orderListByCallNumFor(HttpSession session, int callNum) {
		List<OrderInfo> list = callSelectListService.orderInfoList(callNum);
		return list;
	}
	

//	콜번호로 콜 조회
	@RequestMapping("/getCall.do")
	public @ResponseBody CallInfo callInfoByCallId(int callNum) {
		return callSelectListService.selectCallInfo(callNum);
	}
	
	
	@RequestMapping("/saveFavorite.do") 
	public @ResponseBody boolean saveFavorite(HttpSession session, int addressType, String address, String addrDetail, String receiverName, String receiverPhone) {
		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo"); 
		String csId = csInfo.getCsId();
		FavoriteInfo favInfo = new FavoriteInfo(csId, addressType, address, addrDetail, receiverName, receiverPhone);
		return favoriteManagementService.saveDestination(favInfo);
	}
	
	
	@RequestMapping("/getFavorite.do")
	public String favInfoList(HttpSession session) {
		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo"); 
		String csId = csInfo.getCsId();
		List<FavoriteInfo> list = favoriteManagementService.getDestinationList(csId);
		session.setAttribute("favList", list);
		return "quickApplyPage/quickApply_second";
	}
	
//	페이지로 리턴
	@RequestMapping("/getCallsByCsId")
	public String getCallList(HttpSession session) {
		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo"); 
		String csId = csInfo.getCsId();
		List<CallInfo> list = callManagementService.getCallsByCsId(csId); 
		session.setAttribute("callListByCsId", list);
		return "csPage/UserPage";
	}
	
//	객체로 리턴
	@RequestMapping("/getCallsByCsIds")
	public @ResponseBody List<CallInfo> getCallLists(HttpSession session) {
		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo"); 
		String csId = csInfo.getCsId();
		return callManagementService.getCallsByCsId(csId);
	}
	
	
//	현재 작성중인 콜(오더가 없는) 이 있다면 불러오기.(최신 1개만)
	@RequestMapping("/currentCall.do")
	public @ResponseBody CallInfo selectCall(String csId) {
		return callSelectListService.selectCallInfo(csId);
	}
	
	
	
	
}
