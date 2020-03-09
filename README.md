## TradeRev Auction Bidding System


### Overview:
This is a simple SpringBoot based Java application that performs various operations for a simple auction bidding system.
The application consists of Java 8, SpringBoot, JDBC, H2 in memory database.

### Object Model:
The object model consists of Users, Cars and Bids:
1. *Users*: 
    - **Id**: Database ID
    - **Name**:	Name of User
    - **Licence Number**: Licence number of user (used as the unique identifier)
	
2. *Cars*:
    - **Id**:	Database ID
    - **Model**: Car Model
    - **Year**:	Year of manufacturing
    - **Plate Number**: Plate number of car
    - **VIN**: 	VIN number of car (used as the unique identifier)
	
3. *Bids*:
    - **Id**:				Database ID
    - **Car ID**:			Database ID of Car the bid is for (links to Cars.ID)
    - **User ID**:		Database ID of User that placed the bid (links to Users.ID)
    - **Bid Value**: 		Dollar value of bid
    - **Bid Date**: 		Date bid was placed


### To Run App:
	- Checkout from GIT
	- Import as Maven project in Eclipse
	- Right click on TradeRevAuctionApplication.java class and select 'Run as Java Application'

### REST APIs:
REST API's have been created to perform most of the CRUD functionality such as creating & deleting users, cars and bids:
1. GET operations:
    - USERS:
      - Retrieve list of all users:
        - REQUEST: http://localhost:8080/users
      - Retrieve user by Id:
        - REQUEST: http://localhost:8080/users/{id}
    - CARS:
      - Retrieve list of all cars:
        - REQUEST: http://localhost:8080/cars
      - Retrieve car by Id:
        - REQUEST: http://localhost:8080/cars/{id}
      - Retrieve all bids for a car Id:
        - REQUEST: http://localhost:8080/cars/{id}/bids
      - Retrieve all bids for a user Id:
        - REQUEST: http://localhost:8080/users/{id}/bids
      - Retrieve winning bid on a car:
        - REQUEST: http://localhost:8080/cars/{id}/bids?winning=true
    - BIDS:		
      - Retrieve list of all bids:
        - REQUEST: http://localhost:8080/bids
      - Retrieve bid by Id:
        - REQUEST: http://localhost:8080/bids/{id}
			
2. POST operations:
    - USERS:
      - Save a user:
        - REQUEST: http://localhost:8080/users
        - BODY: {"name":"Oliver Kahn","licenceNumber":"123412754-12312"}
    - CARS:
      - Save a car:
        - REQUEST: http://localhost:8080/cars
        - BODY: {"model":"Golf Jetta","year":"2019","plateNumber":"123W52124","vin":"JM3L2W28A450539854"}
    - BIDS:
      - Save a bid:
        - REQUEST: http://localhost:8080/bids
        - BODY: {"carId":"3","userId":"1","bidValue":"1511","bidDate":"2020-02-12"}

3. DELETE operations:
    - USERS:
      - Delete user by Id:
        - REQUEST: http://localhost:8080/users/{id}
    - CARS:
      - Delete car by Id:
        - REQUEST: http://localhost:8080/cars/{id}
    - BIDS:		
      - Delete bid by Id:
        - REQUEST: http://localhost:8080/bids/{id}
		
### System constraints:
	- To register a bid, the user and the car must exist in the system already
	- The value of a bid placed on car must have a greater value than the current max bid
	- Deleting a user/car requires deleting their underlying bids first

### Improvements:
	- Status can be implemented for car bidding such that only cars that are active can be bid on
	- Http authentication
	- Unit tests




############################################################################################
# Software Developer Coding Challenge

This is a coding challenge for software developer applicants applying through http://work.traderev.com/

## Goal:

#### You have been tasked with building a simple online car auction system which will allow users to bid on cars for sale and with the following funcitionalies: 

  - [ ] Fork this repo. Keep it public until we have been able to review it.
  - [ ] A simple auction bidding system
  - [ ] Record a user's bid on a car
  - [ ] Get the current winning bid for a car
  - [ ] Get a car's bidding history 

 ### Bonus:

  - [ ] Unit Tests on the above functions
  - [ ] Develop a UI on web or mobile or CLI to showcase the above functionality

## Evaluation:

 - [ ] Solution compiles. Provide a README to run/build the project and explain anything that you leave aside
 - [ ] No crashes, bugs, compiler warnings
 - [ ] App operates as intended
 - [ ] Conforms to SOLID principles
 - [ ] Code is easily understood and communicative
 - [ ] Commit history is consistent, easy to follow and understand
