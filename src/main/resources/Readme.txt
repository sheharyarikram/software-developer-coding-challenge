
###################################
TradeRev Auction Bidding System
###################################

###Overview:
This is a simple SpringBoot based Java application that performs various operations for a simple auction bidding system.
The application consists of Java 8, SpringBoot, JDBC, H2 in memory database.

###Object Model:
- The object model consists of Users, Cars and Bids:
	Users: 
		Id: 			Database ID
		Name:			Name of User
		Licence Number: Licence number of user (used as the unique identifier)
	
	Cars:
		Id:				Database ID
		Model:			Car Model
		Year:			Year of manufacturing
		Plate Number: 	Plate number of car
		VIN: 			VIN number of car (used as the unique identifier)
	
	Bids:
		Id:				Database ID
		Car ID:			Database ID of Car the bid is for (links to Cars.ID)
		User ID:		Database ID of User that placed the bid (links to Users.ID)
		Bid Value: 		Dollar value of bid
		Bid Date: 		Date bid was placed


###To Run this app:
	- Checkout from GIT
	- Import as Maven project in Eclipse
	- Right click on TradeRevAuctionApplication.java class and select 'Run as Java Application'

###REST api's have been created to test the CRUD functionality. All operations can be performed via provided REST endpoints such as creating & deleting users, cars and bids:
	1. GET operations:
		a. USERS:
			- Retrieve list of all users:
				REQUEST: http://localhost:8080/users
			- Retrieve user by Id:
				REQUEST: http://localhost:8080/users/{id}
		b. CARS:
			- Retrieve list of all cars:
				REQUEST: http://localhost:8080/cars
			- Retrieve car by Id:
				REQUEST: http://localhost:8080/cars/{id}
			- Retrieve all bids for a car Id:
				REQUEST: http://localhost:8080/cars/{id}/bids
			- Retrieve all bids for a user Id:
				REQUEST: http://localhost:8080/users/{id}/bids
			- Retrieve winning bid on a car:
				REQUEST: http://localhost:8080/cars/{id}/bids?winning=true
		c. BIDS:		
			- Retrieve list of all bids:
				REQUEST: http://localhost:8080/bids
			- Retrieve bid by Id:
				REQUEST: http://localhost:8080/bids/{id}
			
	2. POST operations:
		a. USERS:
			- Save a user:
				REQUEST: http://localhost:8080/users
				BODY: {"name":"Oliver Kahn","licenceNumber":"123412754-12312"}
		b. CARS:
			- Save a car:
				REQUEST: http://localhost:8080/cars
				BODY: {"model":"Golf Jetta","year":"2019","plateNumber":"123W52124","vin":"JM3L2W28A450539854"}
		c. BIDS:
			- Save a bid:
				REQUEST: http://localhost:8080/bids
				BODY: {"carId":"3","userId":"1","bidValue":"1511","bidDate":"2020-02-12"}

	3. DELETE operations:
			a. USERS:
			- Delete user by Id:
				REQUEST: http://localhost:8080/users/{id}
		b. CARS:
			- Delete car by Id:
				REQUEST: http://localhost:8080/cars/{id}
		c. BIDS:		
			- Delete bid by Id:
				REQUEST: http://localhost:8080/bids/{id}
		
###System constraints:
	- To register a bid, the user and the car must exist in the system already
	- The value of a bid placed on car must have a greater value than the current max bid
	- Deleting a user/car requires deleting their underlying bids first

###IMPROVEMENTS:
	- Status can be implemented for car bidding such that only cars that are active can be bid on
	- Http authentication
	- Unit tests




