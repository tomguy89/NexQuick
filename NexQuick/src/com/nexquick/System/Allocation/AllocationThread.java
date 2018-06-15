package com.nexquick.system.allocation;

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
		System.out.println("하나 뽑아옴");
		int repeat = (int) map.get("repeat");
		CallInfo callInfo = (CallInfo) map.get("callInfo");
		List<OnDelivery> orders = callSelectService.orderListByCallNum(callInfo.getCallNum());
		StringBuilder msgBd = new StringBuilder();
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
		String token = csInfoDao.selectCSDevice(callInfo.getCsId());
		if(repeat==3) {
			fireBaseMessaging.sendMessage(token, callInfo.getCallNum()+"번 콜의 배차에 실패했습니다.");
			callInfo.setDeliveryStatus(-1);
			callManagementService.updateCall(callInfo);
			return;
		}
		String addrStr = callInfo.getSenderAddress()+" "+callInfo.getSenderAddressDetail();
		Address addr = addressTransService.getAddress(addrStr);
		String hCode = addr.gethCode();
		System.out.println(hCode);
		if (hCode!=null) {
			qpList = qpPositionService.selectQPListByHCode(addr);
		}else {
			String bCode = addr.getbCode();
			qpList = qpPositionService.selectQPListByBCode(addr);
		}
		
		
		
		if (qpList.size()!=0) {
			System.out.println("repeat : "+repeat);
			for(QPPosition qp : qpList) {
				System.out.println(qp.getQpId());
				//callInfo.setQpId(qp.getQpId());
				//callManagementService.updateCall(callInfo);
				fireBaseMessaging.sendMessage(qp.getConnectToken(), msgBd.toString());
				
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (callSelectService.selectCallInfo(callInfo.getCallNum()).getQpId()!=0) {
					fireBaseMessaging.sendMessage(token, callInfo.getCallNum()+"번 콜의 배차가 완료됐습니다.");
					callInfo.setDeliveryStatus(2);
					callManagementService.updateCall(callInfo);
					break;
				}
			}
		}
		if (callSelectService.selectCallInfo(callInfo.getCallNum()).getQpId()==0) {
			allocationQueue.offer(callInfo, repeat+1);
			System.out.println("다시 넣기");
		}
		/*else {
			sendMessage(token, "죄송합니다. 현재는 배차가 불가능합니다.");
			callInfo.setDeliveryStatus(-1);
			callManagementService.updateCall(callInfo);
		}*/ //기사님이 없을 경우
	
	}
	
	public List<QPPosition> priorityQP(List<QPPosition> qpList){
		
		return qpList;
	}
	
}

