DROP TABLE IF EXISTS Consumer;

CREATE TABLE Consumer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(300) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    role varchar(100) NOT NULL
);

INSERT INTO Consumer (username, password, role) VALUES
    ('Sushi', 'Olive', 'consumer'),
    ('Nutcracker10', 'Mandy', 'consumer');
