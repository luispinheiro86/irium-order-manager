-- Insert users
INSERT INTO users (id, name, email) VALUES (1, 'Joe', 'joe@mail.com');
INSERT INTO users (id, name, email) VALUES (2, 'Bob', 'bob@mail.com');

-- Insert items
INSERT INTO item (id, name) VALUES (1, 'Pencil');
INSERT INTO item (id, name) VALUES (2, 'Plane');

-- Insert stock movements
INSERT INTO stock_movements (id, item_id, quantity, available, creation_date)
VALUES (1, 1, 50, 50, CURRENT_TIMESTAMP),
       (2, 2, 30, 30, CURRENT_TIMESTAMP);

-- Insert orders
INSERT INTO orders (id, user_id, item_id, quantity, status, creation_date)
VALUES (5, 1, 1, 10, 'PENDING', CURRENT_TIMESTAMP),
       (6, 2, 2, 5, 'COMPLETED', CURRENT_TIMESTAMP);
