package com.nexquick.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.QPInfo;
import com.nexquick.model.vo.QPPosition;
import com.nexquick.service.account.QPPositionService;
import com.nexquick.service.parsing.AddressTransService;



@RequestMapping("/qpPosition")
@Controller
public class QPPositionController {

	private QPPositionService qpPositionService;
	private AddressTransService addressTransService;
	
	@Autowired
	public void setQpAccountService(QPPositionService qpPositionService) {
		this.qpPositionService=qpPositionService;
	}
	
	@Autowired
	public void setAddressTransService(AddressTransService addressTransService) {
		this.addressTransService=addressTransService;
	}
	
	
	
	
	@RequestMapping("/updatePosition.do")
	public void qpSignIn(double qpLatitude, double qpLongitude,int qpId, HttpSession session) {//이거 메소드 이름 바꿔야할듯??
		QPPosition qpPosition = new QPPosition();
		qpPosition.setQpId(qpId);
		qpPosition.setQpLatitude(qpLatitude);
		qpPosition.setQpLongitude(qpLongitude);
		
		
		Address address=addressTransService.getAddress(qpLongitude, qpLatitude);
		qpPosition.setbCode(address.getbCode());
		qpPosition.sethCode(address.gethCode());

		qpPositionService.updateQPPosition(qpPosition);
		
	}
	
	@RequestMapping("/insertPosition.do")
	public void insertQPPosition(double qpLatitude, double qpLongitude,int qpId, String connectToken, HttpSession session) {
		QPPosition qpPosition = new QPPosition();
		System.out.println(connectToken);
		qpPosition.setQpId(qpId);
		qpPosition.setQpLatitude(qpLatitude);
		qpPosition.setQpLongitude(qpLongitude);
		qpPosition.setConnectToken(connectToken);
		Address address=addressTransService.getAddress(qpLongitude, qpLatitude);
		qpPosition.setbCode(address.getbCode());
		qpPosition.sethCode(address.gethCode());
		qpPositionService.createQPPosition(qpPosition);
		
	}
	
	@RequestMapping("/deletePosition.do")
	public void deleteQPPosition(String qpId) {
		qpPositionService.deleteQPPosition(Integer.parseInt(qpId));
	}
	
	@RequestMapping("/decline.do")
	public void decline(int qpId) {
		qpPositionService.decline(qpId);
	}
	
	@RequestMapping("/accept.do")
	public void accept(int qpId) {
		qpPositionService.accept(qpId);
	}
	
}
