INSERT INTO users (id, name, licence_number) VALUES
  (1, 'John Doe', 'I5933-10021-32139'),
  (2, 'Jane Doe', '57634-48120-21'),
  (3, 'Alex Smith', '7124120-13123-IF321')
;

INSERT INTO car (id, model, year, plate_number, vin) VALUES
  (1, 'Honda Civic', 2005, 'CKW 461', '1HSHGAER1SH689540'),
  (2, 'Toyota Corolla', 2018, 'JWA 639', '1FAFP45X83F375287'),
  (3, 'Nissan Altima', 2014, 'RAWW 541', 'JN8AS5MV6AW668020'),
  (4, 'Hyundai Elantra', 2014, '516723', 'KL1TD626X4B233696')
;

INSERT INTO bidding (bidding_id, car_id, user_id, bid_value, bid_date) VALUES
  (1, 1, 1, '1000.0', '2020-01-19'),
  (2, 2, 1, '1550.0', '2020-02-19'),
  (3, 3, 1, '1240.50', '2019-12-11'),
  (4, 1, 2, '1200', '2020-01-20'),
  (5, 1, 3, '1250', '2020-02-20'),
  (6, 3, 2, '1510.90', '2020-03-01'),
  (7, 2, 2, '1600', '2020-02-20')
;