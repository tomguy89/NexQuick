package com.nexquick.model.vo;

public class WeatherData {
	
	private String hcode;
	private String bcode;
	private String dong;
	private String weatherInfo;
	private double sensorTemp;
	private double sensorHum;
	private double refLatitude;
	private double refLongitude;
	
	public String getHcode() {
		return hcode;
	}
	public void setHcode(String hcode) {
		this.hcode = hcode;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getWeatherInfo() {
		return weatherInfo;
	}
	public void setWeatherInfo(String weatherInfo) {
		this.weatherInfo = weatherInfo;
	}
	public double getSensorTemp() {
		return sensorTemp;
	}
	public void setSensorTemp(double sensorTemp) {
		this.sensorTemp = sensorTemp;
	}
	public double getSensorHum() {
		return sensorHum;
	}
	public void setSensorHum(double sensorHum) {
		this.sensorHum = sensorHum;
	}
	public double getRefLatitude() {
		return refLatitude;
	}
	public void setRefLatitude(double refLatitude) {
		this.refLatitude = refLatitude;
	}
	public double getRefLongitude() {
		return refLongitude;
	}
	public void setRefLongitude(double refLongitude) {
		this.refLongitude = refLongitude;
	}
	
}
