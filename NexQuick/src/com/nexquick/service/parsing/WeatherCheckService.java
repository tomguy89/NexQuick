package com.nexquick.service.parsing;

import java.util.Map;

import com.nexquick.model.vo.Address;

public interface WeatherCheckService {

	Map<String, String> simpleWeather(Address addr);

	void detailWeather(Address addr);

}