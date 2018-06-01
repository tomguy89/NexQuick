package com.nexquick.service.parsing;

import com.nexquick.model.vo.Address;

public interface WeatherCheckService {

	int simpleWeather(Address addr);

	void detailWeather(Address addr);

}