package com.nexquick.service.call;

import java.util.List;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.CallInfo;
import com.nexquick.model.vo.QPPosition;

public interface AllocationService {

	void allocate(CallInfo callInfo, Address address, List<QPPosition> qpList);

}