package com.nexquick.service.call;

public interface PricingService {

	int setFreightPrice(int freightType, int quant);

	int proportionalPrice(double distance);

	int weightForWeather();
}