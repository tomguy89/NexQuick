package com.nexquick.model.dao;

import com.nexquick.model.vo.QuickData;
import com.nexquick.model.vo.WeatherData;

public interface BigDataDAO {

	void insertWeatherData(WeatherData wd);
	
	void insertQuickData(QuickData qd);
}