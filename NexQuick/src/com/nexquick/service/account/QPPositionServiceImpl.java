package com.nexquick.service.account;

import java.util.List;
import java.util.Map;

import com.nexquick.model.dao.QPPositionDAO;
import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.QPPosition;

public class QPPositionServiceImpl implements QPPositionService {
	
	private QPPositionDAO qpPositionDao;
	public void setQpPositionDao(QPPositionDAO qpPositionDao) {
		this.qpPositionDao = qpPositionDao;
	}
	

	@Override
	public List<QPPosition> selectQPListByBCode(Map<String, Object> param){
		return qpPositionDao.selectQPByBCode(param);
	}
	

	@Override
	public List<QPPosition> selectQPListByHCode(Map<String, Object> param){
		return qpPositionDao.selectQPByHCode(param);
	}
	
//	새로만듦. 0608
	@Override
	public QPPosition getQPPosition(int qpId) {
		return qpPositionDao.selectQPPosition(qpId);
	}
	
	@Override
	public boolean updateQPPosition(QPPosition qpPosition) {
		return qpPositionDao.updateQPPosition(qpPosition);
	}

	//0608 이은진 추가(2)
	@Override
	public boolean createQPPosition(QPPosition qpPosition) {
		
		return qpPositionDao.createQPPosition(qpPosition);
	}


	@Override
	public boolean deleteQPPosition(int qpId) {
		return qpPositionDao.deleteQPPosition(qpId);
	}


	@Override
	public boolean decline(int qpId) {
		return qpPositionDao.decline(qpId);
	}


	@Override
	public boolean accept(int qpId) {
		return qpPositionDao.accept(qpId);
	}

//  0613 김민규추가
	@Override
	public QPPosition selectQPPositionByCallNum(int qpId) {
		return qpPositionDao.selectQPPosition(qpId);
	}


	@Override
	public boolean changeQPStatus(int qpId, int qpStatus) {
		return qpPositionDao.changeQPStatus(qpId, qpStatus);
	}
	
}
