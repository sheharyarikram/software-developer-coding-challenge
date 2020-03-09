package com.trade.rev.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.trade.rev.models.UserVO;

@Component
public class UsersJDBCService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Logger logger = LoggerFactory.getLogger(UsersJDBCService.class);
	
	//Get list of all User table records
    public List<UserVO> findAll() {
        return jdbcTemplate.query("select * from users order by id asc", 
        	(rs, rowNum) -> new UserVO(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("licence_number")
                ));
    }
	
    //Get User record that matches the given unique Id
    public UserVO getUserById(int id) {     	
    	try {
	        return jdbcTemplate.queryForObject("select * from users where id = ?", 
	        		new Integer[] {id}, 
	        		(rs, rowNum) -> new UserVO(
	    	                rs.getInt("id"),
	    	                rs.getString("name"),
	    	                rs.getString("licence_number")));
    	} catch (EmptyResultDataAccessException ex) {
        	logger.info("User record with id {} does not exist!", id);
    		return null;
    	}
    }
    
    //Get User record that matches the given unique licence
    public UserVO getUserByLicence(String licence) {     	
    	try {
	        return jdbcTemplate.queryForObject("select * from users where licence_number = ?", 
	        		new String[] {licence}, 
	        		(rs, rowNum) -> new UserVO(
	    	                rs.getInt("id"),
	    	                rs.getString("name"),
	    	                rs.getString("licence_number")));
    	} catch (EmptyResultDataAccessException ex) {
        	logger.info("User record with licence {} does not exist!", licence);
    		return null;
    	}
    }
  	
    //Save User record 
    public int save(UserVO user) {
        return jdbcTemplate.update("insert into users (id, name, licence_number) values(?, ?, ?)",
        		user.getId(),
        		user.getUserName(),
        		user.getLicenceNumber());
    }
    
    //Update user record 
    public int update(UserVO user) {
        return jdbcTemplate.update("update users set name = ?, licence_number = ? where id = ?",
        		user.getUserName(),
        		user.getLicenceNumber(),
        		user.getId()); //lookup key
    }
    
    //delete user record
    //TODO - caller needs to delete references of user in bidding table also
    //TODO - Users should not be deleted - this is only for testing purposes
    public int deleteById(int userId) {
       return jdbcTemplate.update("delete from users where id = ?", userId);
    }
    
    public Integer getMaxId() {
        return jdbcTemplate.queryForObject("select max(id) from users", Integer.class);
    }
}
