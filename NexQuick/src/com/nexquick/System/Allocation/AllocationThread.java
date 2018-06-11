package com.nexquick.System.Allocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.parsing.AddressTransService;

public class AllocationThread {
	
	private List<QPPosition> qpList;
	private AddressTransService addressTransService;

	@Autowired
	public void setAddressTransService(AddressTransService addressTransService) {
		this.addressTransService = addressTransService;
	}
	
	private QPPositionService qpPositionService;
	@Autowired
	public AddressTransService getAddressTransService() {
		return addressTransService;
	}

	public AllocationThread() {
		Runnable r = new allocateCall();
		Thread t = new Thread(r);
		t.start();
	}
	
	
	class allocateCall implements Runnable{
		AllocationQueue allocationQueue = AllocationQueue.getInstance();
		
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				allocate(allocationQueue.poll());
			}
		}
	}


	private void allocate(CallInfo callInfo) {
		String addrStr = callInfo.getSenderAddress()+" "+callInfo.getSenderAddressDetail();
		Address addr = addressTransService.getAddress(addrStr);
		String hCode = addr.gethCode();
		if (hCode!=null) {
			qpList = qpPositionService.selectQPListByHCode(hCode);
		}else {
			String bCode = addr.getbCode();
			qpList = qpPositionService.selectQPListByBCode(bCode);
		}
		//구현중
		
	}
	
	
	public boolean sendMessage(String token) {
		 final String apiKey = "AAAAgb88Mhk:APA91bELrdask0S2rSfezDKhemJ7UCcA85f4ZzmiUA-sZfHVPG9QHsuMJJToBFHANZhF1_lqsKDrBtgr4Qx08bXUZHmxn3BqCgzcYaDOevVvOHKYzr1C_Ha7J5DFKq5puwq_PyrJCeCg";
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
			String input = "{\"notification\" : {\"title\" : \" 콜 알림 \", \"body\" : \""+"string"+"\"}, \"to\":\""+token+"\"}";
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         // print result
		return true;
	}
	
}

