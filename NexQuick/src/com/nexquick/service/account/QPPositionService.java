package com.nexquick.service.account;

import java.util.List;
import java.util.Map;

import com.nexquick.model.vo.QPPosition;

public interface QPPositionService {

	List<QPPosition> selectQPListByBCode(Map<String, Object> param);

	List<QPPosition> selectQPListByHCode(Map<String, Object> param);

	QPPosition getQPPosition(int qpId);

	boolean updateQPPosition(QPPosition qpPosition);
	boolean createQPPosition(QPPosition qpPosition);
	boolean deleteQPPosition(int qpId);
	boolean decline(int qpId);
	boolean accept(int qpId);

//	0613 김민규 추가
	QPPosition selectQPPositionByCallNum(int qpId);

	boolean changeQPStatus(int qpId, int qpStatus);
}