package com.nexquick.service.account;

import java.util.List;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.QPPosition;

public interface QPPositionService {

	List<QPPosition> selectQPListByBCode(Address addr);

	List<QPPosition> selectQPListByHCode(Address addr);

	QPPosition getQPPosition(int qpId);

	boolean updateQPPosition(QPPosition qpPosition);
	boolean createQPPosition(QPPosition qpPosition);
	boolean deleteQPPosition(int qpId);
	boolean decline(int qpId);
	boolean accept(int qpId);
}