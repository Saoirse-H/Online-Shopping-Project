DROP TABLE IF EXISTS Seller;

CREATE TABLE Seller (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username varchar(100) NOT NULL UNIQUE,
  password varchar(100) NOT NULL,
  role varchar(100) NOT NULL
);

 INSERT INTO Seller (username, password, role) VALUES
  ('craftyGurl', 'Oscar', 'seller'),
  ('craftySwede', 'Sweden', 'seller');

