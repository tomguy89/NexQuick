package com.nexquick.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.QPInfo;
import com.nexquick.service.account.CSAccountService;
import com.nexquick.service.account.QPAccountService;

@RequestMapping("/admin")
@Controller
public class AdminFunctionController {
	

	private CSAccountService csAccountService;
	private QPAccountService qpAccountService;
	@Autowired
	public void setCsAccountService(CSAccountService csAccountService) {
		this.csAccountService = csAccountService;
	}
	
	@Autowired
	public void setQpAccountService(QPAccountService qpAccountService) {
		this.qpAccountService = qpAccountService;
	}

	private boolean adminCheck(HttpSession session) {
		CSInfo csInfo = (CSInfo) session.getAttribute("csInfo");
		if(csInfo.getCsId().equals("admin")) return true;
		else return false;
	}
	
	@RequestMapping("/allCs.do")
	private String allCsList(HttpSession session) {
		List<CSInfo> csList = csAccountService.csAllList();
		session.setAttribute("csList", csList);
		System.out.println(csList.toString());
		return "admin/admin_UserPage";
	}
	
	@RequestMapping("/allQp.do")
	private String allQpList(HttpSession session) {
		List<QPInfo> qpList = qpAccountService.qpAllList();
		session.setAttribute("qpList", qpList);
		return "admin/admin_QuickProPage";
	}
	
	
}
