package com.trade.rev.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.trade.rev.models.BiddingVO;

/*
 * Bidding Data Access Object (REST -> DAO -> JDBC -> H2)
 */
@Component
public class BiddingDAO {
	
	@Autowired
	private BiddingJDBCService biddingJDBC;
	private Logger logger = LoggerFactory.getLogger(BiddingDAO.class);
	
	
	public List<BiddingVO> getAllBids() {
		List<BiddingVO> allBids = biddingJDBC.findAll();
		return allBids;
	}
	
	public List<BiddingVO> getAllBidsForCar(int carId) {
		List<BiddingVO> allBidsForCar = biddingJDBC.findAllBidsByCar(carId);
		return allBidsForCar;
	}
	
	public List<BiddingVO> getAllBidsForUser(int userId) {
		List<BiddingVO> allBidsForUser = biddingJDBC.findAllBidsByUser(userId);
		return allBidsForUser;
	}
	
	public BiddingVO getBiddingById(int id) {
		return biddingJDBC.getBiddingById(id);
	}
	
	public boolean doesBiddingExist(int id) {
		if (biddingJDBC.getBiddingById(id) == null) {
			return false;
		} else {
			return true;
		}
	}

	//Handles both insert and update cases
	public boolean saveBidding(BiddingVO bidding) {
		try { 
			//Check if bidding already exists
			if (doesBiddingExist(bidding.getBiddingId())) {
				//update case
				biddingJDBC.update(bidding);
			} else {
				//insert case
				bidding.setBiddingId(getNextAvailableId());
				biddingJDBC.save(bidding);
			}
		} catch (DataAccessException e) {
        	logger.info("Could not save bidding due to exception: ", e);
			return false;
		}
		
		return true;
	}
	
	//returns null if failed to find max bid for the car
	public BiddingVO getCurrentWinningBidForCar(int carId) {
		return biddingJDBC.getMaxBiddingForCar(carId);
	}
	
	public int deleteBidding(int id) {
		return biddingJDBC.deleteById(id);
	}
	
	public int getNextAvailableId() {
		return (biddingJDBC.getMaxId().intValue() + 1);
	}
}
