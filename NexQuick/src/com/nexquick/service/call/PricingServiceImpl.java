package com.nexquick.service.call;

import com.nexquick.model.dao.PriceInfoDAO;
import com.nexquick.model.vo.PriceInfo;
import com.nexquick.service.parsing.WeatherCheckService;

public class PricingServiceImpl implements PricingService {
	
	private PriceInfoDAO priceDao;
	public void setPriceDao(PriceInfoDAO priceDao) {
		this.priceDao = priceDao;
	}
	private WeatherCheckService weatherCheckService;
	public void setWeatherCheckService(WeatherCheckService weatherCheckService) {
		this.weatherCheckService = weatherCheckService;
	}

	@Override
	public int setFreightPrice(int freightType, int quant) {
		PriceInfo pi = priceDao.selectPrice(freightType);
		int price = pi.getPrice() * (Math.max(quant - pi.getFreeCount(),0));
		return price;
	}
	
	@Override
	public int proportionalPrice(double distance) {
		int price = 3000;
		// 거리 비례한 가격 책정(추후 조정)
		price += Math.pow((int)((Math.max(0, (distance-30)))/10), 2) * 500;
		return price;
	}
	
	@Override
	public int weightForWeather() {
		return 0;
	}
}
