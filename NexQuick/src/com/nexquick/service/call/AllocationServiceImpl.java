package com.nexquick.service.call;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexquick.model.dao.CallInfoDAO;
import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPPosition;

public class AllocationServiceImpl implements AllocationService {
	
	class matchingQP implements Runnable{
		
		List<QPPosition> qpList;
		CallInfo callInfo;
		boolean isMatch = false;
		private CallInfoDAO callInfoDao;
		@Autowired
		public void setCallInfoDao(CallInfoDAO callInfoDao) {
			this.callInfoDao = callInfoDao;
		}

		public matchingQP(CallInfo callInfo, List<QPPosition> qpList) {
			this.callInfo = callInfo;
			this.qpList = qpList;
		}

		@Override
		public void run() {
			while(!isMatch) {
				for(QPPosition qp : qpList) {
					int qpId = qp.getQpId();
					if(sendMessage(qpId)) {
						callInfo.setQpId(qpId);
						callInfoDao.updateCall(callInfo);
						isMatch = true;
					}
				}
			}
		}
		
		public boolean sendMessage(int qpId) {
			
			return true;
		}
	}
	
	@Override
	public void allocate(CallInfo callInfo, Address address, List<QPPosition> qpList) {
		Runnable r = new matchingQP(callInfo, qpList);
		Thread t = new Thread(r);
		t.start();
	}
}

