package com.nexquick.system.allocation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexquick.system.FireBaseMessaging;
import com.nexquick.model.dao.CSInfoDAO;
import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.call.CallManagementService;
import com.nexquick.service.call.CallSelectListService;
import com.nexquick.service.parsing.AddressTransService;

@Service
public class AllocationThread {
	//배차를 담당하는 스레드는 서버가 실행되는 동시에 개별적으로 동작하여
	//Queue에 저장된 콜 정보를 받아와 배차를 실시한다.
	//이를 위해 Dispatcher Servlet에 함께 등록했다.
	
	AllocationQueue allocationQueue = AllocationQueue.getInstance();

	private AddressTransService addressTransService;
	@Autowired
	public void setAddressTransService(AddressTransService addressTransService) {
		this.addressTransService = addressTransService;
	}
	
	private QPPositionService qpPositionService;
	@Autowired
	public void setQpPositionService(QPPositionService qpPositionService) {
		this.qpPositionService = qpPositionService;
	}
	
	private CallManagementService callManagementService;
	@Autowired
	public void setCallManagementService(CallManagementService callManagementService) {
		this.callManagementService = callManagementService;
	}
	
	private CallSelectListService callSelectService;
	@Autowired
	public void setCallSelectService(CallSelectListService callSelectService) {
		this.callSelectService = callSelectService;
	}

	private CSInfoDAO csInfoDao;
	@Autowired
	public void setCsInfoDao(CSInfoDAO csInfoDao) {
		this.csInfoDao = csInfoDao;
	}
	
	FireBaseMessaging fireBaseMessaging = FireBaseMessaging.getInstance();

	public AllocationThread() {
		Runnable r = new allocateCall();
		Thread t = new Thread(r);
		t.start();
	}
	
	
	class allocateCall implements Runnable{
		
		@Override
		public void run() {
			System.out.println("스레드가 생성되었음");
			while(!Thread.currentThread().isInterrupted()) {
				allocate(allocationQueue.poll());
			}
		}
	}


	private void allocate(Map<String, Object> map) {
		List<QPPosition> qpList = null;
		System.out.println("콜 정보 하나 꺼내기");
		
		//몇번째 반복되는 정보인지에 대한 내용, 여러가지 조건에 따른 기사님 배정이 되는데,
		//기사님이 콜을 수락 안하는 등, 배차가 되지 않을 경우 3번까지 큐에 입력되고,
		//그때까지 배차가 되지 않는 경우에는 고객이 배차를 재요청할 수 있도록 했다.
		int repeat = (int) map.get("repeat");
		CallInfo callInfo = (CallInfo) map.get("callInfo");
		List<OnDelivery> orders = callSelectService.orderListByCallNum(callInfo.getCallNum());
		StringBuilder msgBd = new StringBuilder();
		String token = csInfoDao.selectCSDevice(callInfo.getCsId());
		if(repeat==3) {
			fireBaseMessaging.sendMessage(token, callInfo.getCallNum()+"번 콜의 배차에 실패했습니다.");
			callInfo.setDeliveryStatus(-1);
			callManagementService.updateCall(callInfo);
			return;
		}
		
		//QuickPro님들이 사용하시는 용어로 FireBase Messaging을 하기 위해 데이터를 추출하여
		//Message를 작성한다.
		if(orders.get(0).getUrgent()==1) {
			msgBd.append("급/");
		}
		msgBd.append("픽/");
		String temp = orders.get(0).getSenderAddress();
		int t = 0, k = 0;
		for(int i=temp.length()-1; i>=0; i--) {
			if(temp.charAt(i)=='(') t = i;
			if(temp.charAt(i)==')') k = i;
			if(t!=0 && k!=0) break;
		}
		msgBd.append(temp.substring(t+1, k));
		
		for(int i=0; i<orders.size(); i++) {
			msgBd.append("/착");
			if(orders.size()!=1) {
				msgBd.append(" ").append(i+1);
			}
			msgBd.append("/");
			
			temp = orders.get(i).getReceiverAddress();
			t = 0; k = 0;
			for(int j=temp.length()-1; j>=0; j--) {
				if(temp.charAt(j)=='(') t = j;
				if(temp.charAt(j)==')') k = j;
				if(t!=0 && k!=0) break;
			}
			msgBd.append(temp.substring(t+1, k)).append("/");
			msgBd.append(orders.get(i).getFreightList());
		}
		msgBd.append("/");
		msgBd.append(callInfo.getTotalPrice()).append("원@");
		msgBd.append(callInfo.getCallNum());

		//행정구역 및 거리로 우선적으로 QuickPro님들의 배정 순서를 정하기 위해 주소를 가져와
		//AddressTransService에 필요한 정보를 입력하여 Address VO에 담는다.
		String addrStr = callInfo.getSenderAddress()+" "+callInfo.getSenderAddressDetail();
		Address addr = addressTransService.getAddress(addrStr);
		String hCode = addr.gethCode();
		String bCode = addr.getbCode();
		System.out.println(hCode);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("qpId", callInfo.getQpId());
		param.put("hCode", hCode);
		param.put("bCode", bCode);
		param.put("latitude", addr.getLatitude());
		param.put("longitude", addr.getLongitude());
		param.put("vehicleType", callInfo.getVehicleType());
		
		//출근한 기사님 중 위에 해당하는 정보를 보내서 QuickPro님들의 목록을 가져온다.
		if (hCode!=null) {
			qpList = qpPositionService.selectQPListByHCode(param);
		}else {
			
			qpList = qpPositionService.selectQPListByBCode(param);
		}
		
		//긴급의 경우, QuickPro님이 현재 몇개의 주문을 받고 있는지를 고려한다.
		if(callInfo.getUrgent()==1) {
			qpList.sort(new Comparator<QPPosition>() {
				@Override
				public int compare(QPPosition o1, QPPosition o2) {
					return o1.getNow()-o2.getNow();
				}
			});
		}
		
		//QuickPro님에게 Call정보 보내기
		if (qpList.size()!=0) {
			for(QPPosition qp : qpList) {
				//위에서 만든 메시지를 QuickPro님에게 순차적으로 보낸다.
				fireBaseMessaging.sendMessage(qp.getConnectToken(), msgBd.toString());
				//QuickPro님이 메시지를 확인(청취)하고 콜을 수락하는 시간이 필요하므로 잠시 기다린다.
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//CallInfo에 DeliveryStatus를 확인함으로써, 기사님이 이 콜을 수락했는지 확인한다.
				if (callSelectService.selectCallInfo(callInfo.getCallNum()).getDeliveryStatus()==2) {
					fireBaseMessaging.sendMessage(token, callInfo.getCallNum()+"번 콜의 배차가 완료됐습니다.");
					break;
				}
			}
		}
		//만약 배차가 안되었다면, repeat을 1 추가하여 다시 Queue에 넣는다.
		if (callSelectService.selectCallInfo(callInfo.getCallNum()).getQpId()==0) {
			allocationQueue.offer(callInfo, repeat+1);
		}
	}
	
}


