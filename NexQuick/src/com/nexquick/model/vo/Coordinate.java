package com.nexquick.model.vo;

public class Coordinate {
	private String type;
	private int number;
	private double Latitude;
	private double Longitude;
	
	public Coordinate() {
		super();
	}
	
	public Coordinate(String type, int number, double latitude, double longitude) {
		super();
		this.type = type;
		this.number = number;
		Latitude = latitude;
		Longitude = longitude;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public double getLatitude() {
		return Latitude;
	}
	
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	
	public double getLongitude() {
		return Longitude;
	}
	
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	
	
}
