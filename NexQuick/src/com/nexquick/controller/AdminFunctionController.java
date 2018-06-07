package com.nexquick.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexquick.model.vo.CSInfo;
import com.nexquick.model.vo.CallInfo;
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
	
	@RequestMapping("/allCsByName.do")
	private @ResponseBody List<CSInfo> allCsListByName(HttpSession session, String csName) {
		HashMap<String, Object> condition = new HashMap<>();
		if(csName.length() == 0 || csName.equals("")) {
			csName = null;
		}
		condition.put("csName", csName);
		List<CSInfo> csList = csAccountService.csAllListByName(condition);
		return csList;
	}
	
	@RequestMapping("/allQp.do")
	private String allQpList(HttpSession session) {
		List<QPInfo> qpList = qpAccountService.qpAllList();
		session.setAttribute("qpList", qpList);
		return "admin/admin_QuickProPage";
	}
	

	@RequestMapping("/allQpByName.do")
	private @ResponseBody List<QPInfo> allQpListByName(HttpSession session, String qpName) {
		HashMap<String, Object> condition = new HashMap<>();
		if(qpName.length() == 0 || qpName.equals("")) {
			qpName = null;
		}
		condition.put("qpName", qpName);
		List<QPInfo> qpList = qpAccountService.qpAllListByName(condition);
		return qpList;
	}
	
}
