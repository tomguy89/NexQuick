package com.nexquick.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexquick.model.vo.Address;
import com.nexquick.model.vo.WeatherData;
import com.nexquick.service.data.WeatherDataService;
import com.nexquick.service.parsing.AddressTransService;
import com.nexquick.service.parsing.WeatherCheckService;

@RequestMapping("/data")
@Controller
public class BigDataController {
	
	AddressTransService addressTransService;
	@Autowired
	public void setAddressTransService(AddressTransService addressTransService) {
		this.addressTransService = addressTransService;
	}
	
	WeatherCheckService weatherCheckService;
	@Autowired
	public void setWeatherCheckService(WeatherCheckService weatherCheckService) {
		this.weatherCheckService = weatherCheckService;
	}
	
	WeatherDataService weatherDataService;
	@Autowired
	public void setWeatherDataService(WeatherDataService weatherDataService) {
		this.weatherDataService = weatherDataService;
	}



	@RequestMapping("/weatherSensorData.do")
	public void insertWeatherData(double humidity, double temperature, String latitude, String longitude) {
		
		Double lon = Double.parseDouble(longitude);
		Double lat = Double.parseDouble(latitude);
		Address addr = addressTransService.getAddress(lon, lat);
		WeatherData wd = new WeatherData();
		wd.setHcode(addr.gethCode());
		wd.setBcode(addr.getbCode());
		wd.setDong(addr.getDongmyun());
		wd.setRefLatitude(lat);
		wd.setRefLongitude(lon);
		wd.setSensorTemp(temperature);
		wd.setSensorHum(humidity);
		Map<String, String> map = weatherCheckService.simpleWeather(addr);
		wd.setWeatherInfo(map.get("weatherStatus")+"/"+map.get("weatherModify"));
		weatherDataService.insertWeatherData(wd);
	}
	
}
