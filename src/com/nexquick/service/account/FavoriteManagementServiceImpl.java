package com.nexquick.service.account;

import java.util.List;

import com.nexquick.model.dao.FavoriteInfoDAO;
import com.nexquick.model.vo.FavoriteInfo;

public class FavoriteManagementServiceImpl implements FavoriteManagementService {
	
	private FavoriteInfoDAO addressInfoDao;
	public void setFavoriteInfoDao(FavoriteInfoDAO favoriteInfoDao) {
		this.addressInfoDao = favoriteInfoDao;
	}
	
	@Override
	public FavoriteInfo getBasicAddress(String csId) {
		return addressInfoDao.selectAddressList(csId, 1).get(0);
	}
	
	@Override
	public boolean setBasicAddress(FavoriteInfo addressInfo) {
		FavoriteInfo temp  = getBasicAddress(addressInfo.getCsId());
		if(temp!=null) {
			temp.setAddressType(2);
			addressInfoDao.updateAddress(temp);
		}
		addressInfo.setAddressType(1);
		return addressInfoDao.updateAddress(addressInfo);
	}
	
	@Override
	public List<FavoriteInfo> getDepartureList(String csId){
		return addressInfoDao.selectAddressList(csId, 2);
	}
	
	@Override
	public List<FavoriteInfo> getDestinationList(String csId){
		return addressInfoDao.selectAddressList(csId, 3);
	}
}
