DROP TABLE IF EXISTS stock_movements;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS item;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    item_id BIGINT,
    quantity INT,
    status VARCHAR(50),
    creation_date TIMESTAMP,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_item FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE stock_movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT,
    quantity INT,
    available INT,
    creation_date TIMESTAMP,
    CONSTRAINT fk_stock_item FOREIGN KEY (item_id) REFERENCES item(id)
);
