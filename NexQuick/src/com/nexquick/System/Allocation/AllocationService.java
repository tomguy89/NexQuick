package com.nexquick.System.Allocation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.parsing.AddressTransService;

public class AllocationService {
	
	private Queue queue = null;
	private CallInfo callInfo;
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


	public AllocationService() {
		queue = AllocationQueue.getInstance();
	}
	
	
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			callInfo = (CallInfo) queue.pop();
			allocate(callInfo);
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
		
	}
	
	
	public boolean sendMessage(int qpId) {
			
		return true;
	}
	
	//@RequestMapping(value="mobile/sendFCM")
/*    public String index(Model model, HttpServletRequest request, HttpSession session, MobileTokenVO vo)throws Exception{
            
            List<MobileTokenVO> tokenList = fcmService.loadFCMInfoList(vo); 
            
                String token = tokenList.get(count).getDEVICE_ID();
                
                final String apiKey = "AAAAgb88Mhk:APA91bELrdask0S2rSfezDKhemJ7UCcA85f4ZzmiUA-sZfHVPG9QHsuMJJToBFHANZhF1_lqsKDrBtgr4Qx08bXUZHmxn3BqCgzcYaDOevVvOHKYzr1C_Ha7J5DFKq5puwq_PyrJCeCg";
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key=" + apiKey);
 
                conn.setDoOutput(true);
                
                String userId =(String) request.getSession().getAttribute("ssUserId");
 
                // 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
                String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";
                
                // 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
                String input = "{\"notification\" : {\"title\" : \" 여기다 제목넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\" 여기가 받을 사람 토큰  \"}";
 
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
                // print result
                System.out.println(response.toString());
                
 
        return "jsonView";
    }
*/
}

