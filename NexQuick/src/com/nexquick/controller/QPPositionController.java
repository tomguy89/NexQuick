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
	public void qpSignIn(double qpLatitude, double qpLongitude,String qpId, HttpSession session) {
			
		//
		System.out.println("이클립스 updatePosition에 들어왔음");
		System.out.println("qpId : "+qpId);
		System.out.println("qpLongitudeqpLongitude: "+qpLongitude);
		System.out.println("qpLatitude: "+qpLatitude);
		//
		
		QPPosition qpPosition = new QPPosition();
		qpPosition.setQpId(Integer.parseInt(qpId));
		qpPosition.setQpLatitude(qpLatitude);
		qpPosition.setQpLongitude(qpLongitude);
		
		
		Address address=addressTransService.getAddress(qpLongitude, qpLatitude);
		qpPosition.setbCode(address.getbCode());
		qpPosition.sethCode(address.gethCode());
		
		//
		System.out.println("법정코드는"+address.getbCode());
		System.out.println("행정코드는"+address.gethCode());
		//
		
		qpPositionService.updateQPPosition(qpPosition);
		
	}
	
	
	
}
