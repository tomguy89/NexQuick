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
	
	@RequestMapping("/allCsSearch.do")
	private @ResponseBody List<CSInfo> allCsListBySearch(HttpSession session, String csName, String csBusinessName, String csDepartment, int csGrade, int csType) {
		HashMap<String, Object> condition = new HashMap<>();
		if(csName.length() == 0 || csName.equals("")) {
			csName = null;
		}
		if(csBusinessName.length() == 0 || csBusinessName.equals("")) {
			csBusinessName = null;
		}
		if(csDepartment.length() == 0 || csDepartment.equals("")) {
			csDepartment = null;
		}
		
		condition.put("csName", csName);
		condition.put("csBusinessName", csBusinessName);
		condition.put("csDepartment", csDepartment);
		condition.put("csGrade", csGrade);
		condition.put("csType", csType);
		
		List<CSInfo> csList = csAccountService.csAllListBySearch(condition);
		return csList;
	}
	
	@RequestMapping("/allQp.do")
	private String allQpList(HttpSession session) {
		List<QPInfo> qpList = qpAccountService.qpAllList();
		session.setAttribute("qpList", qpList);
		return "admin/admin_QuickProPage";
	}
	

	@RequestMapping("/allQpSearch.do")
	private @ResponseBody List<QPInfo> allQpListSearch(HttpSession session, String qpName, int qpVehicleType) {
		HashMap<String, Object> condition = new HashMap<>();
		if(qpName.length() == 0 || qpName.equals("")) {
			qpName = null;
		}
		condition.put("qpName", qpName);
		condition.put("qpVehicleType", qpVehicleType);
		List<QPInfo> qpList = qpAccountService.qpAllListSearch(condition);
		return qpList;
	}
	
	
//	0615 김민규추가
	@RequestMapping("/updateCSByCSId")
	private @ResponseBody boolean updateCSByCSId(String csId, int csGrade) {
		CSInfo csInfo = csAccountService.getCSInfo(csId);
		csInfo.setCsGrade(csGrade);
		return csAccountService.csGradeModify(csInfo);
	}
	
}
