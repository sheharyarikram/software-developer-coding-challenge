package com.trade.rev.models;

/*
 * User object model
 */
public class UserVO {
	
	private int id;
	private String name; //Format: alphabetic only
	private String licenceNumber; //Unique identifier of the user - format: LXXXX-FFFMY-YMMDD
	
	public UserVO(int id, String name, String licenceNumber) {
		super();
		this.id = id;
		this.name = name;
		this.licenceNumber = licenceNumber;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int userId) {
		this.id = userId;
	}
	
	public String getUserName() {
		return name;
	}
	
	public void setUserName(String userName) {
		this.name = userName;
	}
	
	public String getLicenceNumber() {
		return licenceNumber;
	}
	
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}	

}
