DROP TABLE IF EXISTS Cart;

CREATE TABLE Cart (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  item_id INT NOT NULL,
  price DOUBLE NOT NULL,
  quantity INT NOT NULL
);