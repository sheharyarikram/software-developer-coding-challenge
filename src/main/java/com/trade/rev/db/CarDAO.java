package com.trade.rev.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.trade.rev.models.CarVO;

/*
 * User Data Access Object (REST -> DAO -> JDBC -> H2)
 */
@Component
public class CarDAO {

	@Autowired
	private CarJDBCService carJDBC;
	private Logger logger = LoggerFactory.getLogger(CarDAO.class);


	public List<CarVO> getAllCars() {
		List<CarVO> allCars = carJDBC.findAll();
		return allCars;
	}

	public CarVO getCarById(int id) {
		return carJDBC.getCarById(id);
	}
	
	public CarVO getCarByVin(String vin) {
		return carJDBC.getCarByVin(vin);
	}

	public boolean doesCarExist(String vin) {
		if (carJDBC.getCarByVin(vin) == null) {
			return false;
		} else {
			return true;
		}
	}

	//Handles both insert and update cases
	public boolean saveCar(CarVO car) {
		try { 
			//Check if car already exists
			if (doesCarExist(car.getVin())) {
				//update case
				carJDBC.update(car);
			} else {
				//insert case
				car.setId(getNextAvailableId());
				carJDBC.save(car);
			}
		} catch (DataAccessException e) {
			logger.info("Could not save car record due to exception: ", e);
			return false;
		}

		return true;
	}

	public int deleteCar(int id) {
		return carJDBC.deleteById(id);
	}

	public int getNextAvailableId() {
		return (carJDBC.getMaxId().intValue() + 1);
	}
}
