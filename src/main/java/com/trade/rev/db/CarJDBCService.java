package com.trade.rev.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.trade.rev.models.CarVO;

@Component
public class CarJDBCService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Logger logger = LoggerFactory.getLogger(UsersJDBCService.class);

	//Get list of all Car table records
	public List<CarVO> findAll() {
		return jdbcTemplate.query("select * from car order by id asc", 
				(rs, rowNum) -> new CarVO(
						rs.getInt("id"),
						rs.getString("model"),
						rs.getInt("year"),
						rs.getString("plate_number"),
						rs.getString("vin")
						));
	}

	//Get Car record that matches the given unique Id
	public CarVO getCarById(int id) {     	
		try {
			return jdbcTemplate.queryForObject("select * from car where id = ?", 
					new Integer[] {id}, 
					(rs, rowNum) -> new CarVO(
							rs.getInt("id"),
							rs.getString("model"),
							rs.getInt("year"),
							rs.getString("plate_number"),
							rs.getString("vin")));
		} catch (EmptyResultDataAccessException ex) {
			logger.info("Car record with id {} does not exist!", id);
			return null;
		}
	}
	
	//Get Car record that matches the given unique VIN
	public CarVO getCarByVin(String vin) {     	
		try {
			return jdbcTemplate.queryForObject("select * from car where vin = ?", 
					new String[] {vin}, 
					(rs, rowNum) -> new CarVO(
							rs.getInt("id"),
							rs.getString("model"),
							rs.getInt("year"),
							rs.getString("plate_number"),
							rs.getString("vin")));
		} catch (EmptyResultDataAccessException ex) {
			logger.info("Car record with vin {} does not exist!", vin);
			return null;
		}
	}

	//Save Car record 
	public int save(CarVO car) {
		return jdbcTemplate.update("insert into car (id, model, year, plate_number, vin) values(?, ?, ?, ?, ?)",
				car.getId(),
				car.getModel(),
				car.getYear(),
				car.getPlateNumber(),
				car.getVin());
	}

	//Update car record 
	public int update(CarVO car) {
		return jdbcTemplate.update("update car set model = ?, year = ?, plate_number = ?, vin = ? where id = ?",
				car.getModel(),
				car.getYear(),
				car.getPlateNumber(),
				car.getVin(),
				car.getId()); //lookup key
	}

	//delete car record
	//TODO - caller needs to delete references of car in bidding table also
	//TODO - Cars should not be deleted - this is only for testing purposes
	public int deleteById(int userId) {
		return jdbcTemplate.update("delete from car where id = ?", userId);
	}

	public Integer getMaxId() {
		return jdbcTemplate.queryForObject("select max(id) from car", Integer.class);
	}
}
