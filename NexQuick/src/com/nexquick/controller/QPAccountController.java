package com.nexquick.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nexquick.system.UploadPath;
import com.nexquick.model.vo.OnDelivery;
import com.nexquick.model.vo.QPInfo;
import com.nexquick.model.vo.QPPay;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.QPAccountService;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.call.CallSelectListService;

@RequestMapping("/qpAccount")
@Controller
public class QPAccountController {

	private QPAccountService qpAccountService;

	@Autowired
	public void setQpAccountService(QPAccountService qpAccountService) {
		this.qpAccountService = qpAccountService;
	}

	private QPPositionService qpPositionService;

	@Autowired
	public void setQpPositionService(QPPositionService qpPositionService) {
		this.qpPositionService = qpPositionService;
	}

	private CallSelectListService callSelectListService;

	@Autowired
	public void setCallSelectListService(CallSelectListService callSelectListService) {
		this.callSelectListService = callSelectListService;
	}

	/**
	 * QP의 로그인을 처리 contextPath/qpAccount/qpSignIn.do
	 * 
	 * @param qpId:고객아이디
	 * @param qpPassword:비밀번호
	 * @param session:세션에
	 *            qpInfo 저장
	 * @return JSON ([비동기 통신] 로그인 성공:true / 로그인 실패:false)
	 */
	@RequestMapping("/qpSignIn.do")
	public @ResponseBody QPInfo qpSignIn(String qpPhone, String qpPassword, HttpSession session) {
		QPInfo qpInfo = qpAccountService.qpSignIn(qpPhone, qpPassword);
		if (qpInfo != null) {
			session.setAttribute("qpInfo", qpInfo);
			return qpInfo;
		} else
			return null;
	}

	/**
	 * 세션에 저장된 로그인 정보를 invalidate 처리 contextPath/qpAccount/signOut.do
	 * 
	 * @param session
	 * @return view page path (메인 화면)
	 */
	@RequestMapping("/signOut.do")
	public String signOut(HttpSession session) {
		session.invalidate();
		return "";
	}

	/**
	 * 신규 가입 시 아이디 중복 체크 contextPath/qpAccount/qpPhoneDuplCheck.do
	 * 
	 * @param qpPhone:qp전화번호
	 * @return JSON (아이디 사용 가능 시:true / 아이디 중복 시:false)
	 */
	@RequestMapping("/qpPhoneDuplCheck.do")
	public @ResponseBody boolean qpPhoneDuplCheck(String qpPhone) {
		return qpAccountService.qpPhoneDuplicateCheck(qpPhone);
	}

	@RequestMapping("/qpSignUp.do")
	public @ResponseBody boolean qpSignUp(String qpPhone, String qpPassword, String qpName, String qpLicense,
			int vehicleType, String qpProfile, String qpBank, String qpAccount) {

		QPInfo qpInfo = new QPInfo(qpPassword, qpName, qpPhone, "면허", vehicleType, "사진", qpAccount, qpBank);

		if (qpAccountService.qpSignUp(qpInfo))
			return true;
		else
			return false;
	}

	// 새로추가 0608
	@RequestMapping("/getQPPosition.do")
	public @ResponseBody QPPosition getQPPosition(int qpId) {
		return qpPositionService.getQPPosition(qpId);
	}

	// 0612 이은진 추가.
	/*
	 * 1. qpId로 bank찾아오기 2. qpId로 unpayedcall갖고오기 3. unpayedcall의 콜넘으로 총액 갖고오기 4.
	 * unpyedcall의 콜넘으로 결제상태 업데이트하기
	 */

/*	@RequestMapping("/processPayment.do")
	public @ResponseBody QPPay processPayment(int qpId) {

		System.out.println("processPayment 컨트롤러에 들어옴");

		QPInfo qpInfo = qpAccountService.selectQPAccountById(qpId);

		System.out.println(qpInfo.toString());

		QPPay qpPay = new QPPay();
		qpPay.setQpBank(qpInfo.getQpBank());
		qpPay.setQpAccount(qpInfo.getQpAccount());

		List<OnDelivery> orderList = callSelectListService.selectUnpayedCall(qpId);
		int orderListSize = orderList.size();

		if (orderListSize > 0) {
			List<Integer> list = new ArrayList<>();

			for (int i = 0; i < orderListSize; i++) {
				list.add(orderList.get(i).getOrderNum());
			}

			int money = callSelectListService.selectUnpayedSum(list);
			qpPay.setMoney(money);

			updatePaystatus(list);
		}

		return qpPay;

	}*/

	public void updatePaystatus(List<Integer> list) {
		System.out.println("updatePayStatus 컨트롤러에 들어옴");
		callSelectListService.updatePayStatus(list);
	}

	// 0613 새로추가
	@RequestMapping("/getQPByCallNum.do")
	public @ResponseBody QPPosition qpPositionByCallNum(int callNum) {
		QPInfo qpInfo = qpAccountService.getQPByCallNum(callNum);
		int qpId = qpInfo.getQpId();
		return qpPositionService.selectQPPositionByCallNum(qpId);
	}

	// 0614 이은진 추가 (사진 업로드 부분) 위에 signup 부분도.. 수정했음!

	@RequestMapping(value = "/uploadPicture.do", method = RequestMethod.POST)
	public ResponseEntity<String> uploadP(HttpServletRequest request, MultipartFile file1, String StringParameter1) {

		ResponseEntity<String> entity = null;

		String UPLOAD_PATH = "uploadPicture/picture/";

		System.out.println("uploadPicture.do에 들어왔다.");

		System.out.println("스트링 파라매터가 들어왔다 : " + StringParameter1);

		try {

			// UploadPath.attach_path=UPLOAD_PATH;
			String path = UploadPath.path(request);
			String fileName = "driverPicture" + StringParameter1 + ".jpg";

			System.out.println("path는" + path);
			System.out.println("fileName은" + fileName);

			// photoNum++; //이 photoNum은 signup.do에서 업로드되게하쟈....

			if (!file1.isEmpty()) { // 첨부파일이 존재하면
				// 첨부파일의 이름
				// fileName=file1.getOriginalFilename();
				try {
					// 디렉토리 생성
					new File(path).mkdir();

					// 지정된 업로드 경로로 저장됨
					file1.transferTo(new File(path + fileName));

					System.out.println("최종 : " + path + fileName);

					entity = new ResponseEntity<String>("success", HttpStatus.OK);

					qpAccountService.updateProfile(fileName, StringParameter1);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value = "/uploadLicense.do", method = RequestMethod.POST)
	public ResponseEntity<String> uploadL(HttpServletRequest request, MultipartFile file1, String StringParameter1) {

		ResponseEntity<String> entity = null;

		System.out.println("uploadLicense.do에 들어왔다.");

		String UPLOAD_PATH = "uploadPicture/license/";

		System.out.println("스트링 파라매터가 들어왔다 : " + StringParameter1);

		try {

			// UploadPath.attach_path=UPLOAD_PATH;
			String path = UploadPath.path(request);
			String fileName = "driverLicense" + StringParameter1 + ".jpg";

			System.out.println("path는" + path);
			System.out.println("fileName은" + fileName);

			// photoNum++; //이 photoNum은 signup.do에서 업로드되게하쟈....

			if (!file1.isEmpty()) { // 첨부파일이 존재하면
				// 첨부파일의 이름
				// fileName=file1.getOriginalFilename();
				try {
					// 디렉토리 생성
					new File(path).mkdir();

					// 지정된 업로드 경로로 저장됨
					file1.transferTo(new File(path + fileName));
					System.out.println("최종 : " + path + fileName);

					entity = new ResponseEntity<String>("success", HttpStatus.OK);
					qpAccountService.updateLicense(fileName, StringParameter1);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@RequestMapping("/unpayedMoney.do")
	public @ResponseBody List<Integer> unpayedMoney(int qpId) {

		List<OnDelivery> deliveryList = callSelectListService.selectUnpayedCall(qpId);

		Set<Integer> callSet = new HashSet<>();

		for (OnDelivery d : deliveryList) {
			callSet.add(d.getCallNum());
		}
		
		List<Integer> jungsanList = new ArrayList<>();
		jungsanList.addAll(callSet);
		
		int inapp = callSelectListService.selectUnpayedSumInApp(jungsanList);
		int place = callSelectListService.selectUnpayedSumPlace(jungsanList);
		
		jungsanList.clear();
		jungsanList.add(inapp);
		jungsanList.add(place);

		return jungsanList;

	}
	
	@RequestMapping("/processPayment.do")
	public @ResponseBody QPPay processPayment(int qpId, int jungsan) {
	
	
		System.out.println("processPayment 컨트롤러에 들어옴");
		List<OnDelivery> deliveryList = callSelectListService.selectUnpayedCall(qpId);

		Set<Integer> callSet = new HashSet<>();

		for (OnDelivery d : deliveryList) {
			callSet.add(d.getCallNum());
		}
		
		List<Integer> jungsanList = new ArrayList<>();
		jungsanList.addAll(callSet);
		QPInfo qpInfo = qpAccountService.selectQPAccountById(qpId);

		System.out.println(qpInfo.toString());

		QPPay qpPay = new QPPay();
		qpPay.setQpBank(qpInfo.getQpBank());
		qpPay.setQpAccount(qpInfo.getQpAccount());
	
		updatePaystatus(jungsanList);


	return qpPay;
		
	}

	
	@RequestMapping("/processDepositSubtr.do")
	public @ResponseBody int processDepositSubtr(int qpId, int jungsan) {
	
	
		System.out.println("processDepositSubtr 컨트롤러에 들어옴");
		
		List<OnDelivery> deliveryList = callSelectListService.selectUnpayedCall(qpId);

		Set<Integer> callSet = new HashSet<>();

		for (OnDelivery d : deliveryList) {
			callSet.add(d.getCallNum());
		}
		
		List<Integer> jungsanList = new ArrayList<>();
		jungsanList.addAll(callSet);
		QPInfo qpInfo = qpAccountService.selectQPAccountById(qpId);
		int money = qpInfo.getQpDeposit() - jungsan;
		
		System.out.println("보증금 : " + money +"원");
		

	
		updatePaystatus(jungsanList);


	return money;
		
	}
	
	@RequestMapping("/getQP.do")
	public @ResponseBody QPInfo getQP(int qpId) {
		QPInfo qpInfo = qpAccountService.selectQP(qpId);
		QPInfo qpInfo2 = qpAccountService.selectQPAccountById(qpId);
			
		qpInfo.setQpBank(qpInfo2.getQpBank());
		qpInfo.setQpAccount(qpInfo2.getQpAccount());
		return qpInfo;
	}
			
	@RequestMapping("/qpInfoUpdate.do")
	public @ResponseBody boolean qpInfoUpdate(int qpId, String qpPassword,
			int vehicleType, String qpBank, String qpAccount) {

		QPInfo qpInfo = qpAccountService.selectQP(qpId);
		qpInfo.setQpPassword(qpPassword);
		qpInfo.setQpVehicleType(vehicleType);
		qpInfo.setQpBank(qpBank);
		qpInfo.setQpAccount(qpAccount);

		if (qpAccountService.qpModify(qpInfo)&& qpAccountService.qpAccountModify(qpInfo))
			return true;
		else
			return false;
	}
	
	
	public @ResponseBody boolean changeQPStatus(int qpId, int qpStatus) {
		return qpPositionService.changeQPStatus(qpId, qpStatus);
	}


	@RequestMapping("/qpUpdateProfileOnly.do")
	public @ResponseBody boolean qpUpdate(int qpId, String qpProfile) {
		QPInfo qpInfo = qpAccountService.selectQP(qpId);
		qpInfo.setQpProfile(qpProfile);
		qpAccountService.updateProfileOnly(qpInfo);
		return true;
	}
	
}
