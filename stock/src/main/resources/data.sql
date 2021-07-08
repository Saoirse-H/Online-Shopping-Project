DROP TABLE IF EXISTS Item;

CREATE TABLE Item (
  item_id INT AUTO_INCREMENT  PRIMARY KEY,
  name varchar(100) NOT NULL,
  category varchar(100) NOT NULL,
  description varchar(250),
  price DOUBLE NOT NULL,
  quantity INT NOT NULL,
  seller_id INT NOT NULL

);

 INSERT INTO Item (name, category, description, price, quantity, seller_id) VALUES
  ('Swedish Sock', 'Socks', 'Yeety Swedish Socks', '100', '10', '2'),
  ('Blue Sock', 'Socks', 'blue socks', '200', '1000', '2'),
  ('Red Sock', 'Socks', 'Very red', '500', '2000', '2'),
  ('Swedish Hat', 'Hats', 'Swedish Hat', '100', '10', '1'),
  ('Red Hat', 'Hats', null, '100', '10', '1'),
  (' Swedish Shoe', 'Shoes', 'Nice shoe', '100', '10', '1'),
  ('Yeet Coat', 'Coats', 'Not so swedish', '50','10', '2');
