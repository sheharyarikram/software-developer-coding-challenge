package com.trade.rev.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.trade.rev.db.BiddingDAO;
import com.trade.rev.db.CarDAO;
import com.trade.rev.db.UsersDAO;
import com.trade.rev.enums.ErrorCodesEnum;
import com.trade.rev.models.BiddingVO;
import com.trade.rev.models.CarVO;
import com.trade.rev.models.HttpErrorVO;
import com.trade.rev.models.UserVO;


/*
 * REST service class to dispatch calls to data layer
 */
@Component
public class AuctionRestService {

	@Autowired
	private BiddingDAO biddingDAO;
	@Autowired
	private CarDAO carDAO;
	@Autowired
	private UsersDAO userDAO;
	
	private Logger logger = LoggerFactory.getLogger(AuctionRestService.class);
	
	private static final String VALID_NAME_PATTERN = "^[ A-Za-z]+$";
	private static final String VALID_LICENCE_PATTERN = "^[a-zA-Z0-9-_]+$";
	private static final String VALID_ALPHANUMERIC_PATTERN = "[^A-Za-z0-9]";
	private static final String VALID_PLATE_PATTERN = "[^A-Za-z0-9]+$";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public List<BiddingVO> getAllBids() {
		//TODO - error handling
		return biddingDAO.getAllBids();
	}

	public List<CarVO> getAllCars() {
		return carDAO.getAllCars();
	}

	public List<UserVO> getAllUsers() {
		return userDAO.getAllUsers();
	}

	public ResponseEntity<?> getAllBidsForCar(int carId, String requestId) {	
		List<BiddingVO> bids = biddingDAO.getAllBidsForCar(carId);
		if (bids == null || bids.isEmpty()) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.BIDDING_NOT_FOUND, "Bidding(s) for car with ID " + carId + " not found!");
		} else {
			logger.info("{} Bids for car with id {} found", bids.size(), carId);
			return new ResponseEntity<List<BiddingVO>>(bids, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getAllBidsForUser(int userId, String requestId) {
		List<BiddingVO> bids = biddingDAO.getAllBidsForUser(userId);
		if (bids == null || bids.isEmpty()) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.BIDDING_NOT_FOUND, "Bid(s) for user with ID " + userId + " not found!");
		} else {
			logger.info("{} Bids for user with id {} found", bids.size(), userId);
			return new ResponseEntity<List<BiddingVO>>(bids, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getWinningBidForCar(int carId, String requestId) {
		BiddingVO bidding = biddingDAO.getCurrentWinningBidForCar(carId);
		if (bidding == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.BIDDING_NOT_FOUND, "Winning bid for car with ID " + carId + " not found!");
		} else {
			logger.info("Winning bid(s) for car with id {} found", carId);
			return new ResponseEntity<BiddingVO>(bidding, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getBidding(int id, String requestId) {
		BiddingVO bidding = biddingDAO.getBiddingById(id);
		if (bidding == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.BIDDING_NOT_FOUND, "Bidding with ID " + id + " not found!");
		} else {
			logger.info("Biddig with id {} found", bidding.getBiddingId());
			return new ResponseEntity<BiddingVO>(bidding, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getCar(int id, String requestId) {
		CarVO car = carDAO.getCarById(id);
		if (car == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.CAR_NOT_FOUND, "Car with ID " + id + " not found!");
		} else {
			logger.info("Car with id {} found", car.getId());
			return new ResponseEntity<CarVO>(car, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getUser(int id, String requestId) {
		UserVO user = userDAO.getUserById(id);
		if (user == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.USER_NOT_FOUND, "User with ID " + id + " not found!");
		} else {
			logger.info("User with id {} found", user.getId());
			return new ResponseEntity<UserVO>(user, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<?> deleteBidding(int id, String requestId) {
		logger.info("Attempting to delete bidding with id {}", id);

		if (biddingDAO.deleteBidding(id) < 1) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.BIDDING_NOT_FOUND, "Bidding does not exist");
		} else {
			return new ResponseEntity<>("Deleted bidding successfully", HttpStatus.OK);
		}
	}

	//Delete user only if user does not have existing bids
	//TODO - check for only active bids
	public ResponseEntity<?> deleteUser(int id, String requestId) {
		logger.info("Attempting to delete user with id {}", id);

		List<BiddingVO> allBidsForUser = biddingDAO.getAllBidsForUser(id);

		if (allBidsForUser != null && !allBidsForUser.isEmpty()) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.USER_HAS_BIDS, "User cannot be deleted as it has existing bids");
		} else {
			if (userDAO.deleteUser(id) < 1) {
				return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.USER_NOT_FOUND, "User does not exist");
			} else {
				return new ResponseEntity<>("Deleted user successfully", HttpStatus.OK);
			}
		}
	}

	//Delete car only if car does not have existing bids
	//TODO - check for only active bids
	public ResponseEntity<?> deleteCar(int id, String requestId) {
		logger.info("Attempting to delete car with id {}", id);

		List<BiddingVO> allBidsForCar = biddingDAO.getAllBidsForCar(id);

		if (allBidsForCar != null && !allBidsForCar.isEmpty()) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.CAR_HAS_BIDS, "Car cannot be deleted as it has existing bids");
		} else {
			if (carDAO.deleteCar(id) < 1) {
				return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.CAR_NOT_FOUND, "Car does not exist");
			} else {
				return new ResponseEntity<>("Deleted car successfully", HttpStatus.OK);
			}
		}
	}

	public ResponseEntity<?> saveUser(UserVO user, String requestId) {	
		//Check if all required fields are provided
		if (user == null ||
			user.getLicenceNumber() == null || user.getLicenceNumber().isEmpty() ||
			user.getUserName() == null || user.getUserName().isEmpty()) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.FIELD_MISSING, "A required field missing or empty");
		}

		//Check if user's name is in valid format
		if (!doesStringMatchPattern(VALID_NAME_PATTERN, user.getUserName())) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "User name is incorrect format: " + user.getUserName());
		}
		
		//Check if licence number is valid is letters only
		if (!doesStringMatchPattern(VALID_LICENCE_PATTERN, user.getLicenceNumber())) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Licence number can only contain letters, alphabets and hyphens");
		}
		
		//Check if user with the same licence number already exists
		if (userDAO.getUserByLicence(user.getLicenceNumber()) != null) {
			return generateErrorResponseEntity(requestId, HttpStatus.CONFLICT, ErrorCodesEnum.USER_EXISTS, "User with this licence number already exists");
		}
		
		logger.info("Attempting to save user with id = {}, name = {}, licence number = {}", 
				user.getId(),
				user.getUserName(),
				user.getLicenceNumber()
				);

		//Save the user (handles both insert and update)
		if (userDAO.saveUser(user)) {
			return new ResponseEntity<UserVO>(user, HttpStatus.CREATED);
		} else {
			return generateErrorResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodesEnum.SERVER_ERROR, "Failed to save user - something went wrong!");
		}
	}

	public ResponseEntity<?> saveCar(CarVO car, String requestId) {	
		//Check if all required fields are provided
		if (car == null ||
			car.getModel() == null || car.getModel().isEmpty() ||
			car.getPlateNumber() == null || car.getPlateNumber().isEmpty() ||
			car.getVin() == null || car.getVin().isEmpty()) {
				return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.FIELD_MISSING, "A required field missing or empty");
		}

		//Check if plate number has special characters
		if (doesStringMatchPattern(VALID_PLATE_PATTERN, car.getPlateNumber())) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Car Plate number has special characters: " + car.getPlateNumber());
		}

		//Check if plate number has special characters
		if (doesStringMatchPattern(VALID_ALPHANUMERIC_PATTERN, car.getVin())) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Special characters are not allowed for car VIN " + car.getVin());
		}

		//Check if car year is 4 character number only			
		if (String.valueOf(car.getYear()).length() != 4) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Car year is in incorrect format: " + car.getYear());
		}
		
		//Check if car with the same VIN number already exists
		if (carDAO.getCarByVin(car.getVin()) != null) {
			return generateErrorResponseEntity(requestId, HttpStatus.CONFLICT, ErrorCodesEnum.CAR_EXISTS, "Car with this VIN number already exists");
		}

		logger.info("Attempting to save car with id = {}, model = {}, plate number = {}, year = {}, vin = {}", 
				car.getId(),
				car.getModel(),
				car.getPlateNumber(),
				car.getYear(),
				car.getVin()				
				);

		//Save the car (handles both insert and update)
		if (carDAO.saveCar(car)) {
			return new ResponseEntity<CarVO>(car, HttpStatus.CREATED);
		} else {
			return generateErrorResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodesEnum.SERVER_ERROR, "Failed to save car - something went wrong!");
		}
	}


	public ResponseEntity<?> saveBidding(BiddingVO bid, String requestId) {	
		//Check if all required fields are provided
		if (bid == null ||
			bid.getCarId() < 1 || bid.getUserId() < 1 ||
			bid.getBidValue() <= 0 || bid.getBidDate() == null) {
				return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.FIELD_MISSING, "A required field missing or empty");
		}

		try {
			//Convert bidding date to correct format (YYYY-MM-DD)
			setBiddingDateFormat(bid);
		} catch (ParseException e) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Bidding date is in incorrect format: " + bid.getBidDate());
		}

		//Check if user exists
		if (userDAO.getUserById(bid.getUserId()) == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.USER_NOT_FOUND, "User with ID " + bid.getUserId() + " not found!");
		}

		//Check if car exists
		if (carDAO.getCarById(bid.getCarId()) == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.CAR_NOT_FOUND, "Car with ID " + bid.getCarId() + " not found!");
		}

		//Check if the bid entered is greater than current max bid
		BiddingVO currentMaxBid = biddingDAO.getCurrentWinningBidForCar(bid.getCarId());
		if (bid.getBidValue() <= currentMaxBid.getBidValue()) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BID_VALUE_INVALID, 
					"Input bid value for this car has to be greater than current winning bid of " + currentMaxBid.getBidValue() + "!");
		}

		logger.info("Attempting to save bidding with id = {}, user id = {}, car id = {}, bidding value = {}, bidding date = {}", 
				bid.getBiddingId(),
				bid.getUserId(),
				bid.getCarId(),
				bid.getBidValue(),
				bid.getBidDate()				
				);

		//Save the car (handles both insert and update)
		if (biddingDAO.saveBidding(bid)) {
			return new ResponseEntity<BiddingVO>(bid, HttpStatus.CREATED);
		} else {
			return generateErrorResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodesEnum.SERVER_ERROR, "Failed to save bidding - something went wrong!");
		}
	}	
	
	//Returns true if input string matches the specified pattern
	public boolean doesStringMatchPattern(String pattern, String input) {
		if (input == null || input.isEmpty() || pattern == null || pattern.isEmpty()) {
			return false;
		}
		
		if (Pattern.compile(pattern).matcher(input).find()) {
			return true;
		} else {
			return false;
		}
	}

	public void setBiddingDateFormat(BiddingVO bid) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		bid.setBidDate(format.parse(format.format(bid.getBidDate())));
	}

	public String generateNewId() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	public ResponseEntity<?> generateErrorResponseEntity(String requestId, HttpStatus httpStatus, ErrorCodesEnum internalCode, String description) {
		return new ResponseEntity<HttpErrorVO>(
				new HttpErrorVO(
						requestId, 
						httpStatus.value(), 
						httpStatus.getReasonPhrase(), 
						description, 
						internalCode.code
						),
				httpStatus
			);
	}


}
