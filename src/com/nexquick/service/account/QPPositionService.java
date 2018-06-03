package com.nexquick.service.account;

import java.util.List;

import com.nexquick.model.vo.QPPosition;

public interface QPPositionService {

	List<QPPosition> selectQPListByBCode(String bCode);

	List<QPPosition> selectQPListByHCode(String hCode);

}