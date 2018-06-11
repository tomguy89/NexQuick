package com.nexquick.System.Allocation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexquick.model.dao.CallInfoDAO;
import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPPosition;

public class RegistCall{
	
	private Queue queue;
	private CallInfo callInfo;
	
	public void RegistCall(CallInfo callInfo) {
		queue = AllocationQueue.getInstance();
		this.callInfo = callInfo;
		Runnable r = new addCall();
		Thread t = new Thread(r);
		t.start();
	}
	
	class addCall implements Runnable{
		
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				queue.put(callInfo);
			}
		}
	}
	
}
