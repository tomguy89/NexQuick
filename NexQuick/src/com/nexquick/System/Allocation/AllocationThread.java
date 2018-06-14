package com.nexquick.System.Allocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		msgBd.append("픽/").append(orders.get(0).getSenderAddress());
		
		for(int i=0; i<orders.size(); i++) {
			msgBd.append("/착");
			if(orders.size()!=1) {
				msgBd.append(" ").append(i+1);
			}
			msgBd.append("/").append(orders.get(i).getReceiverAddress());
			msgBd.append(orders.get(i).getFreightList());
		}
		msgBd.append("/");
		msgBd.append(callInfo.getTotalPrice()).append("원@");
		msgBd.append(callInfo.getCallNum());
		String token = csInfoDao.selectCSDevice(callInfo.getCsId());
		if(repeat==3) {
			sendMessage(token, "배차에 실패하였습니다.");
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
				sendMessage(qp.getConnectToken(), msgBd.toString());
				
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (callSelectService.selectCallInfo(callInfo.getCallNum()).getQpId()!=0) {
					sendMessage(token, "배차가 완료되었습니다.");
					callInfo.setDeliveryStatus(2);
					callManagementService.updateCall(callInfo);
					break;
				}
			}
			if (callSelectService.selectCallInfo(callInfo.getCallNum()).getQpId()==0) {
				allocationQueue.offer(callInfo, repeat+1);
				System.out.println("다시 넣기");
			}
		}else {
			sendMessage(token, "죄송합니다. 현재는 배차가 불가능합니다.");
			callInfo.setDeliveryStatus(-1);
			callManagementService.updateCall(callInfo);
		}
		
		//구현중
	}
	
	
	public boolean sendMessage(String token, String msg) {
		 final String apiKey = "AAAAqMvnVrg:APA91bE3yZF2YLilNUtx2AzvAJy_q2EXrgQR-_7tRYwCIQzFwAyV88BcFeeJa3fZZy_-eOBUZ7W6N1LNUmgTV2g8dvjpCx-QW7jtel6blFP1cKG3CWtPtxV-yUI4dmsz_l4IHWE_KueC";
         URL url;
		try {
			url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);
			
			conn.setDoOutput(true);
			
			// 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
			String input = "{\"notification\" : {\"title\" : \" NexQuick \", \"body\" : \""+""+msg+""+"\"}, \"to\":\""+token+"\"}";
			System.out.println(input);
			
			OutputStream os = conn.getOutputStream();
			
			// 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + input);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}

