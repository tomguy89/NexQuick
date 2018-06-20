package com.nexquick.service.data;

import com.nexquick.model.dao.BigDataDAO;
import com.nexquick.model.vo.WeatherData;

public class WeatherDataServiceImpl implements WeatherDataService {
	
	BigDataDAO bigDataDao;
	public void setBigDataDao(BigDataDAO bigDataDao) {
		this.bigDataDao = bigDataDao;
	}
	
	@Override
	public void insertWeatherData(WeatherData wd) {
		bigDataDao.insertWeatherData(wd);
	}
}
