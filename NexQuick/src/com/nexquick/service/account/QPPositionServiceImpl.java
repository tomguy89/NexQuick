package com.nexquick.service.account;

import java.util.List;

import com.nexquick.model.dao.QPPositionDAO;
import com.nexquick.model.vo.QPPosition;

public class QPPositionServiceImpl implements QPPositionService {
	
	private QPPositionDAO qpPositionDao;
	public void setQpPositionDao(QPPositionDAO qpPositionDao) {
		this.qpPositionDao = qpPositionDao;
	}
	

	@Override
	public List<QPPosition> selectQPListByBCode(String bCode){
		return qpPositionDao.selectQPbybCode(bCode);
	}
	

	@Override
	public List<QPPosition> selectQPListByHCode(String hCode){
		return qpPositionDao.selectQPbyhCode(hCode);
	}
	
//	새로만듦. 0608
	@Override
	public QPPosition getQPPosition(int qpId) {
		return qpPositionDao.selectQPPosition(qpId);
	}
	
}
