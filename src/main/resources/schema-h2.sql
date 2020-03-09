CREATE TABLE users (
   --id			INTEGER AUTO_INCREMENT PRIMARY KEY,
  id				INTEGER PRIMARY KEY,
  name				VARCHAR(255) NOT NULL,
  licence_number	VARCHAR(255) NOT NULL
);
  
CREATE TABLE car (
  id			INTEGER PRIMARY KEY,
  model			VARCHAR(255) NOT NULL,
  year	 		INTEGER NOT NULL,
  plate_number	VARCHAR(255) NOT NULL,
  vin 			VARCHAR(255) NULL
);
  
CREATE TABLE bidding (
  bidding_id	INTEGER PRIMARY KEY,
  car_id		INTEGER NOT NULL, --TODO: Foreign key reference to car table
  user_id 		INTEGER NOT NULL, --TODO: Foreign key reference to user table
  bid_value		VARCHAR(255) NOT NULL,
  bid_date	 	DATE NOT NULL
);