package com.trade.rev.models;

/*
 * Object model for Car
 */
public class CarVO {
	
	private int id;
	private String model; //Eg. Honda Civic
	private int year;  //Eg. 1989
	private String plateNumber; //licence plate number of car
	private String vin; //Unique identifier: vehicle identification number (17 digit, capital letters and numbers. Eg: '1HSHGAER1SH689540')
	
	public CarVO(int id, String model, int year, String plateNumber, String vin) {
		super();
		this.id = id;
		this.model = model;
		this.year = year;
		this.plateNumber = plateNumber;
		this.vin = vin;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
