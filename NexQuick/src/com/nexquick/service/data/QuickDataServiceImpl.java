package com.nexquick.service.data;

import com.nexquick.model.dao.BigDataDAO;
import com.nexquick.model.vo.QuickData;

public class QuickDataServiceImpl implements QuickDataService {
	
	BigDataDAO bigDataDao;
	public void setBigDataDao(BigDataDAO bigDataDao) {
		this.bigDataDao = bigDataDao;
	}
	
	@Override
	public void insertQuickData(QuickData qd) {
		bigDataDao.insertQuickData(qd);
	}
}
