package com.trade.rev.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trade.rev.models.BiddingVO;
import com.trade.rev.models.CarVO;
import com.trade.rev.models.UserVO;


@RestController
public class AuctionController {
		
	@Autowired
	private AuctionRestService auctionRestService;
	
	private Logger logger = LoggerFactory.getLogger(AuctionController.class);
	
	//URL: http://localhost:8080/
	@RequestMapping("/")
	public String healthCheck() {
		return "Hello!";
	}
	
	
	/*
	 * 
	 * USER REST calls
	 */
	
	//URL: http://localhost:8080/users
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private List<UserVO> getAllUsers(HttpServletRequest request) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to get list of all users - Request ID: {}", requestId);
        return auctionRestService.getAllUsers();
    }
    
	//URL: http://localhost:8080/users/1
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getUser(@PathVariable(required = true) int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to retrieve user with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.getUser(id, requestId);
    }
    
	//URL: http://localhost:8080/users/1/bids
    @RequestMapping(value = "/users/{id}/bids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getBidsByUser(@PathVariable(required = true) int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to retrieve bidding for car with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.getAllBidsForUser(id, requestId);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> saveUser(@RequestBody UserVO user) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received POST request to save user with licence number: {} - Request ID: {}", user.getLicenceNumber(), requestId);
    	return auctionRestService.saveUser(user, requestId);
    }
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received DELETE request to delete user with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.deleteUser(id, requestId);
    }
    
    
	/*
	 * 
	 * CAR REST calls
	 */
    
	//URL: http://localhost:8080/cars
    @RequestMapping(value = "/cars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private List<CarVO> getAllCars(HttpServletRequest request) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to get list of all cars - Request ID: {}", requestId);
        return auctionRestService.getAllCars();
    }
	
	//URL: http://localhost:8080/cars/1
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getCar(@PathVariable(required = true) int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to retrieve car with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.getCar(id, requestId);
    }
    
    //Returns either all bids on a car or a winning bid if 'winning' flag is specified with value of true
	//URL: http://localhost:8080/cars/1/bids
	//URL: http://localhost:8080/cars/1/bids?winning=true
    @RequestMapping(value = "/cars/{id}/bids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getBidsByCar(@PathVariable(required = true) int id, 
    										   @RequestParam(value = "winning") Optional<Boolean> winning) {
    	String requestId = auctionRestService.generateNewId();
    	
    	if (winning.isPresent() && winning.get().equals(Boolean.TRUE)) {
    		logger.info("Received GET request to retrieve winning bidding for car with id: {} - Request ID: {}", id, requestId);
    		return auctionRestService.getWinningBidForCar(id, requestId);	
    	} else {
        	logger.info("Received GET request to retrieve bidding for car with id: {} - Request ID: {}", id, requestId);
        	return auctionRestService.getAllBidsForCar(id, requestId);	
    	}
    }
    
    @RequestMapping(value = "/cars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> saveUser(@RequestBody CarVO car) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received POST request to save car with vin: {} - Request ID: {}", car.getVin(), requestId);
    	return auctionRestService.saveCar(car, requestId);
    }
    
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> deleteCar(@PathVariable("id") int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received DELETE request to delete car with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.deleteCar(id, requestId);
    }
    
    
	/*
	 * 
	 * BIDDING REST calls
	 */
    
	//URL: http://localhost:8080/bids
    @RequestMapping(value = "/bids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private List<BiddingVO> getAllBids(HttpServletRequest request) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to get list of all bids - Request ID: {}", requestId);
        return auctionRestService.getAllBids();
    }
    
	//URL: http://localhost:8080/bids/1
    @RequestMapping(value = "/bids/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getBidding(@PathVariable(required = true) int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received GET request to retrieve bidding with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.getBidding(id, requestId);
    }

    @RequestMapping(value = "/bids", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> saveBidding(@RequestBody BiddingVO bidding) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received POST request to save bidding for car id {} by user {} with value {}: {} - Request ID: {}", bidding.getCarId(), bidding.getUserId(), bidding.getBidValue(), requestId);
    	return auctionRestService.saveBidding(bidding, requestId);
    }
    
    @RequestMapping(value = "/bids/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> deleteBidding(@PathVariable("id") int id) {
    	String requestId = auctionRestService.generateNewId();
    	logger.info("Received DELETE request to delete bidding with id: {} - Request ID: {}", id, requestId);
    	return auctionRestService.deleteBidding(id, requestId);
    }
   
}
