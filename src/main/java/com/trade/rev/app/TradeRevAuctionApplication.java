package com.trade.rev.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.trade.rev.*"})
public class TradeRevAuctionApplication {
	/*
	 * To check if app is up, try: http://localhost:8080/actuator/health
	 */

	public static void main(String[] args) {
		SpringApplication.run(TradeRevAuctionApplication.class, args);
	}
	
	//TODO - Implement status of auction/bidding.
	//		 Car object has status flag to specify whether it is active/inactive for bidding (alternative is to have expiry on bids and modify status flag based on the date)
	//TODO - Unit tests
	//TODO - Authentication

}
