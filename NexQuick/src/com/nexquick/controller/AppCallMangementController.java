package com.nexquick.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.system.allocation.AllocationQueue;
import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.FavoriteInfo;
import com.nexquick.model.vo.FreightInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.OrderInfo;
import com.nexquick.model.vo.QPInfo;
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
@RequestMapping("/appCall")
@Controller
public class AppCallMangementController {

	
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
	public @ResponseBody CallInfo newCall(String csId, String senderName, String senderAddress, String senderAddressDetail, String senderPhone,
						  int vehicleType, int urgent, int reserved, int series, String reservationTime) {
		System.out.println(reservationTime);
		//퀵 신청하기에서 주문자 정보 입력
		Address addr = addressTransService.getAddress(senderAddress);
		CallInfo callInfo = new CallInfo(csId, senderName, senderAddress, senderAddressDetail, senderPhone, vehicleType, urgent, series, reserved, reservationTime, Double.parseDouble(addr.getLatitude()), Double.parseDouble(addr.getLongitude()));
		if(reserved == 0) {
			reservationTime = "";
			callInfo.setReservationTime(reservationTime);
			callManagementService.newCallNow(callInfo);
		} else {
			callManagementService.newCall(callInfo);
		}
		
		//새로 생성된 콜 주문번호 세션에 저장
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
	public @ResponseBody OrderInfo addOrder(String csId, int callNum,
				String receiverName, String receiverAddress, String receiverAddressDetail, String receiverPhone, String memo) {
		
		double distance = 0;
		//입력 받은 주문 정보 생성
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		Address sendAddr = addressTransService.getAddress(callInfo.getSenderAddress());
		Address recvAddr = addressTransService.getAddress(receiverAddress+" " + receiverAddressDetail);
		Map<String, Object> point = new LinkedHashMap<>();
		point.put("startX", sendAddr.getLongitude());
		point.put("startY", sendAddr.getLatitude());
		point.put("endX", recvAddr.getLongitude());
		point.put("endY", recvAddr.getLatitude());
		if(!sendAddr.getLatitude().equals(recvAddr.getLatitude()) && !sendAddr.getLongitude().equals(recvAddr.getLongitude())) {
			distance = distanceCheckService.singleDistanceCheck(point);
		}
		int price = pricingService.proportionalPrice(distance);
		OrderInfo orderInfo = new OrderInfo(callNum, receiverName, receiverAddress, receiverAddressDetail, receiverPhone, memo, price, distance, Double.parseDouble(recvAddr.getLatitude()), Double.parseDouble(recvAddr.getLongitude()));
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
	public @ResponseBody FreightInfo addFreight(String csId, int orderNum, int freightType, int freightQuant, String freightDetail){
		//빈칸 처리
		if(freightDetail == null || freightDetail.trim().length()==0) freightDetail = null;
		int price = pricingService.setFreightPrice(freightType, freightQuant);
		FreightInfo freightInfo = new FreightInfo(orderNum, freightType, freightQuant, price, freightDetail);
		if(callManagementService.addFreight(freightInfo)) {
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
	public @ResponseBody boolean delFreight(String csId, int freightNum){
		System.out.println(freightNum);
		FreightInfo freightInfo = callSelectListService.selectFreight(freightNum);
		OrderInfo orderInfo = callSelectListService.selectOrder(freightInfo.getOrderNum());
		orderInfo.setOrderPrice(orderInfo.getOrderPrice()-freightInfo.getFreightPrice());
		return callManagementService.delFreights(freightNum);
	}
	
	@RequestMapping("/getFreight.do")
	public @ResponseBody FreightInfo getFreight(int freightNum){
		System.out.println(freightNum);
		return callManagementService.getFreight(freightNum);
	}
	
	
	
	/**
	 * DB에 저장된 주문정보를 삭제
	 * contextPath/call/delOrder.do
	 * @param orderNum:삭제할 주문 번호
	 * @return JSON (정상처리:true / 비정상처리:false)
	 */
	@RequestMapping("/delOrder.do")
	public @ResponseBody boolean delOrder(HttpSession session, int orderNum) {
		return delOrderProcess(orderNum);
	}
	
	
	/**
	 * 콜 취소 시, DB에 저장해놨던 임시 정보들(콜, 오더, 화물) 삭제
	 * contextPath/call/cancelCall.do
	 * @param session
	 */
	@RequestMapping("/cancelCall.do")
	public @ResponseBody boolean cancelCall(int callNum) {
		List<OrderInfo> orderInfoList = callSelectListService.orderInfoList(callNum);
		for(OrderInfo oi : orderInfoList) {
			delOrderProcess(oi.getOrderNum());
		}
		return callManagementService.delCall(callNum);
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
	public @ResponseBody boolean registCall(int callNum, int payType, int payStatus, int totalPrice) {
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		callInfo.setDeliveryStatus(1);
		callInfo.setTotalPrice(totalPrice);
		callInfo.setPayType(payType);
		callInfo.setPayStatus(payStatus);
		callManagementService.updateCall(callInfo);
		AllocationQueue.getInstance().offer(callInfo, 0);
		return true;
	}
	
	@RequestMapping("/updateCall.do")
	public @ResponseBody boolean updateCall(int callNum, String senderName, String senderAddress, String senderAddressDetail, String senderPhone,
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
	public @ResponseBody boolean delPastCall() {
		callManagementService.delPastCall();
		return true;
	}
	
	
	/**
	 * 주문 삭제를 위한 메서드(재사용성을 위해 메서드 분리)
	 * @param session
	 * @param orderNum
	 * @return 정상처리:true / 비정상처리:false
	 */
	public boolean delOrderProcess(int orderNum) {
		if(callManagementService.delFreights(orderNum)) {
			return callManagementService.delOrder(orderNum);
		}else {
			return false;
		}
	}
	
//	콜번호로 오더들 조회(마지막페이지)
	@RequestMapping("/getOrders.do")
	public Map<String, Object> orderListByCallNum(int callNum, int payType, int payStatus) {
		List<OrderInfo> list = callSelectListService.orderInfoList(callNum);
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
//	콜번호로 오더들 조회(신청조회 페이지)
	@RequestMapping("/getOrderList.do")
	public @ResponseBody List<OrderInfo> orderListByCallNumFor(int callNum) {
		List<OrderInfo> list = callSelectListService.orderInfoList(callNum);
		return list;
	}
	

//	콜번호로 콜 조회
	@RequestMapping("/getCall.do")
	public @ResponseBody CallInfo callInfoByCallId(int callNum) {
		return callSelectListService.selectCallInfo(callNum);
	}
	
	
	@RequestMapping("/saveFavorite.do") 
	public @ResponseBody boolean saveFavorite(String csId, int addressType, String address, String addrDetail, String receiverName, String receiverPhone) { 
		FavoriteInfo favInfo = new FavoriteInfo(csId, addressType, address, addrDetail, receiverName, receiverPhone);
		return favoriteManagementService.saveDestination(favInfo);
	}
	
	
	@RequestMapping("/getFavorite.do")
	public @ResponseBody List<FavoriteInfo> favInfoList(String csId, int addressType) {
		if (addressType == 1) {
			return favoriteManagementService.getDepartureList(csId);
		}else {
			return favoriteManagementService.getDestinationList(csId); 			
		}
	}
	
//	객체로 리턴
	@RequestMapping("/getCallsByIdAndDate")
	public @ResponseBody List<CallInfo> getCallList(String csId, String callTime) {
		if(callTime.length() == 0 || callTime.equals("")) {
			callTime = null;
		}
		System.out.println(callTime);
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("csId", csId);
		condition.put("callTime", callTime);
		List<CallInfo> list = callManagementService.getAllCallsByIdAndDate(condition); 
		return list;
	}

	
	
//	객체로 리턴
	@RequestMapping("/getCallsByNameAndDate")
	public @ResponseBody List<CallInfo> getCallListByNameAndDate(String senderName, String callTime) {
		if(callTime.length() == 0 || callTime.equals("")) {
			callTime = null;
		}
		if(senderName.length() == 0 || senderName.equals("")) {
			senderName = null;
		}
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("senderName", senderName);
		condition.put("callTime", callTime);
		List<CallInfo> list = callManagementService.getAllCallsByNameAndDate(condition); 
		return list;
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
	public @ResponseBody CallInfo selectCall(HttpSession session, String csId) {
		CallInfo callInfo = callSelectListService.selectCallInfo(csId);
		session.setAttribute("callNum", callInfo.getCallNum());
		return callInfo; 
	}
	
	
	
	
//	배송기사 이름 불러오기
	@RequestMapping("/getQPInfo.do")
	public @ResponseBody QPInfo getQPName(int callNum) {
		QPInfo qpInfo = callManagementService.getQPInfo(callNum);
		return qpInfo;
	}
	
	@RequestMapping("/orderListByQPId")
	public @ResponseBody List<OnDelivery> orderListByQPId(int qpId) {
		return callSelectListService.orderListByQPId(qpId); 
	}
	
	// 0612 추가
	@RequestMapping("/saveFavoriteDeparture.do")
	public @ResponseBody boolean saveFavInfo(String csId, int addressType, String address, String addrDetail,
			String receiverName, String receiverPhone) {
		List<FavoriteInfo> favList = favoriteManagementService.getDepartureList(csId); // addressType가 1인 주소 가져오기
		if (favList.size() == 0) { // 없으면 생성
			FavoriteInfo favInfo = new FavoriteInfo(csId, addressType, address, addrDetail, receiverName,
					receiverPhone);
			favoriteManagementService.saveDeparture(favInfo);
			return true;
		} else { // 있으면 업데이트
			int favId = favList.get(0).getFavoriteId();
			System.out.println(favId);
			FavoriteInfo favInfo = new FavoriteInfo(favId, csId, addressType, address, addrDetail, receiverName,
					receiverPhone);
			favoriteManagementService.updateAddress(favInfo);
			return false;
		}
	}

	// 0612 추가
	@RequestMapping("/getFavoriteDeparture.do")
	public @ResponseBody List<FavoriteInfo> DepartFavInfo(String csId) {
		List<FavoriteInfo> favList = favoriteManagementService.getDepartureList(csId); // addressType 1인 주소 가져오기
		if (favList.size() == 0) { // 없으면 생성
			return null;
		} else { // 있으면 업데이트
			return favList;
		}
	}

	// 0612 새로추가!
	@RequestMapping("/cancelOrders.do")
	public @ResponseBody boolean cancelOrdersByCallNum(HttpSession session, int callNum) {
		List<OrderInfo> orderInfoList = callSelectListService.orderInfoList(callNum);
		for (OrderInfo oi : orderInfoList) {
			delOrderProcess(oi.getOrderNum());
		}
		return true;
	}
	
	
	@RequestMapping("/completeAllocate.do")
	public @ResponseBody boolean completeAllocate(int callNum, int qpId) {
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		callInfo.setDeliveryStatus(2);
		callInfo.setQpId(qpId);
		return callManagementService.updateCall(callInfo);
	}
	
	
	@RequestMapping("/reRegistCall.do")
	public @ResponseBody boolean reRegistCall(int callNum) {
		CallInfo callInfo = callSelectListService.selectCallInfo(callNum);
		callInfo.setDeliveryStatus(1);
		callManagementService.updateCall(callInfo);
		AllocationQueue.getInstance().offer(callInfo, 0);
		return true;
	}
	
	@RequestMapping("/getOrderListLast.do")
	public @ResponseBody List<OnDelivery> getOrderListLast(int callNum) {
		List<OnDelivery> list = callSelectListService.getOnDeliveryCallListLast(callNum);
		return list;
	}
	
	@RequestMapping("/getOndeliveryByIdAndDate.do")
	public @ResponseBody List<OnDelivery> getOndeliveryList(String csId, String callTime) {
		if(callTime.length() == 0 || callTime.equals("")) {
			callTime = null;
		}
		System.out.println(callTime);
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("csId", csId);
		condition.put("callTime", callTime);
		List<OnDelivery> list = callManagementService.getAllOndeliveryList(condition); 
		return list;
	}
	
	@RequestMapping("/getQPCallByIdAndDate.do")
	public @ResponseBody List<OnDelivery> getQPCallByIdAndDate(int qpId, String callTime) {
		if(callTime.length() == 0 || callTime.equals("")) {
			callTime = null;
		}
		HashMap<String, Object> condition = new HashMap<>();
		condition.put("qpId", qpId);
		condition.put("callTime", callTime);
		List<OnDelivery> list = callManagementService.getQPCallByIdAndDate(condition); 
		return list;
	}
	
	
	
}
