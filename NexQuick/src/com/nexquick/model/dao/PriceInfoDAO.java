package com.nexquick.model.dao;

import com.nexquick.model.vo.PriceInfo;

public interface PriceInfoDAO {
	
	boolean createPrice(PriceInfo priceInfo);
	boolean updatePrice(PriceInfo priceInfo);
	boolean deletePrice(int freightType);
	PriceInfo selectPrice(int freightType);
}
