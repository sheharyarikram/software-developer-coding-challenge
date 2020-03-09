package com.trade.rev.models;

import java.util.Date;

/*
 * Car Bidding object model
 */
public class BiddingVO {
	
	private int biddingId; 		//unique Bidding Id
	private int carId;			//carId == car.Id
	private int userId;			//userId == user.Id
	private double bidValue;
	private Date bidDate;
	
	public BiddingVO(int biddingId, int carId, int userId, double bidValue, Date bidDate) {
		super();
		this.biddingId = biddingId;
		this.carId = carId;
		this.userId = userId;
		this.bidValue = bidValue;
		this.bidDate = bidDate;
	}

	public int getBiddingId() {
		return biddingId;
	}
	
	public void setBiddingId(int biddingId) {
		this.biddingId = biddingId;
	}
	
	public int getCarId() {
		return carId;
	}
	
	public void setCarId(int carId) {
		this.carId = carId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public double getBidValue() {
		return bidValue;
	}
	
	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}
	
	public Date getBidDate() {
		return bidDate;
	}
	
	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

}
