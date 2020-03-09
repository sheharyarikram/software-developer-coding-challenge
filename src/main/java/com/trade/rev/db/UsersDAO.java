package com.trade.rev.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.trade.rev.models.UserVO;

/*
 * User Data Access Object (REST -> DAO -> JDBC -> H2)
 */
@Component
public class UsersDAO {
		
	@Autowired
	private UsersJDBCService userJDBC;
	private Logger logger = LoggerFactory.getLogger(UsersDAO.class);


	public List<UserVO> getAllUsers() {
		List<UserVO> allUsers = userJDBC.findAll();
		return allUsers;
	}

	public UserVO getUserById(int id) {
		return userJDBC.getUserById(id);
	}
	
	public UserVO getUserByLicence(String licence) {
		return userJDBC.getUserByLicence(licence);
	}

	public boolean doesUserExist(String licence) {
		if (userJDBC.getUserByLicence(licence) == null) {
			return false;
		} else {
			return true;
		}
	}

	//Handles both insert and update cases
	public boolean saveUser(UserVO user) {
		try { 
			//Check if user already exists
			if (doesUserExist(user.getLicenceNumber())) {
				//update case
				userJDBC.update(user);
			} else {
				//insert case
				user.setId(getNextAvailableId());
				userJDBC.save(user);
			}
		} catch (DataAccessException e) {
			logger.info("Could not save user due to exception: ", e);
			return false;
		}

		return true;
	}

	public int deleteUser(int id) {
		return userJDBC.deleteById(id);
	}

	public int getNextAvailableId() {
		return (userJDBC.getMaxId().intValue() + 1);
	}
}
