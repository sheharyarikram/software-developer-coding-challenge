package com.trade.rev.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.trade.rev.models.BiddingVO;

/*
 * JDBC functionality for Bidding table
 */
@Component
public class BiddingJDBCService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Logger logger = LoggerFactory.getLogger(BiddingJDBCService.class);
	
	//Get list of all Bidding table records
    public List<BiddingVO> findAll() {
        return jdbcTemplate.query("select * from bidding order by bidding_id asc", 
        	(rs, rowNum) -> new BiddingVO(
                rs.getInt("bidding_id"),
                rs.getInt("car_id"),
                rs.getInt("user_id"),
                rs.getDouble("bid_value"),
                rs.getDate("bid_date")
                ));
    }
	
	//Get list of all Bidding table records for a specific car
    public List<BiddingVO> findAllBidsByCar(int carId) {
        return jdbcTemplate.query("select * from bidding where car_id = ? order by bidding_id asc", 
    			new Integer[] {carId}, 
    			(rs, rowNum) -> new BiddingVO(
    	                rs.getInt("bidding_id"),
    	                rs.getInt("car_id"),
    	                rs.getInt("user_id"),
    	                rs.getDouble("bid_value"),
    	                rs.getDate("bid_date")
    				)); 
    }
    
	//Get list of all Bidding table records for a specific car
    public List<BiddingVO> findAllBidsByUser(int userId) {
        return jdbcTemplate.query("select * from bidding where user_id = ? order by bidding_id asc", 
    			new Integer[] {userId}, 
    			(rs, rowNum) -> new BiddingVO(
    	                rs.getInt("bidding_id"),
    	                rs.getInt("car_id"),
    	                rs.getInt("user_id"),
    	                rs.getDouble("bid_value"),
    	                rs.getDate("bid_date")
    				)); 
    }
	
    //Save Bidding record 
    public int save(BiddingVO bidding) {
        return jdbcTemplate.update("insert into bidding (bidding_id, car_id, user_id, bid_value, bid_date) values(?, ?, ?, ?, ?)",
        		bidding.getBiddingId(),
        		bidding.getCarId(),
        		bidding.getUserId(),
        		bidding.getBidValue(),
        		bidding.getBidDate());
    }
    
    //Update bidding record 
    public int update(BiddingVO bidding) {
        return jdbcTemplate.update("update bidding set car_id = ?, user_id = ?, bid_value = ?, bid_date = ? where bidding_id = ?",
        		bidding.getCarId(),
        		bidding.getUserId(),
        		bidding.getBidValue(),
        		bidding.getBidDate(),
        		bidding.getBiddingId()); //lookup key
    }
	
    //Get BiddingVO record that matches the given unique Id
    public BiddingVO getBiddingById(int id) {     	
    	try {
	        return jdbcTemplate.queryForObject("select * from bidding where bidding_id = ?", 
	        		new Integer[] {id}, 
	        		(rs, rowNum) -> new BiddingVO(
	    	                rs.getInt("bidding_id"),
	    	                rs.getInt("car_id"),
	    	                rs.getInt("user_id"),
	    	                rs.getDouble("bid_value"),
	    	                rs.getDate("bid_date")));
    	} catch (EmptyResultDataAccessException ex) {
        	logger.info("Bidding record with id {} does not exist!", id);
    		return null;
    	}
    }
    
    //TODO - need to test this!   
    public BiddingVO getMaxBiddingForCar(int carId) {
    	try {
	        return jdbcTemplate.queryForObject("select * from bidding where bid_value = select max(bid_value) from bidding where car_id = ?", 
	        		new Integer[] {carId}, 
	        		(rs, rowNum) -> new BiddingVO(
	    	                rs.getInt("bidding_id"),
	    	                rs.getInt("car_id"),
	    	                rs.getInt("user_id"),
	    	                rs.getDouble("bid_value"),
	    	                rs.getDate("bid_date")));
    	} catch (EmptyResultDataAccessException ex) {
        	logger.info("Failed to find max bid for car Id {}", carId);
    		return null;
    	}
    }
    
    //delete bidding record
    public int deleteById(int biddingId) {
       return jdbcTemplate.update("delete from bidding where bidding_id = ?", biddingId);
    }
    
    public Integer getMaxId() {
        return jdbcTemplate.queryForObject("select max(bidding_id) from bidding", Integer.class);
    }
}
