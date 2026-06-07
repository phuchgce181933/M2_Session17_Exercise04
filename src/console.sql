CREATE DATABASE shop_db;
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE,
                          price DECIMAL(10,2) NOT NULL
);
CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE
);
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        customer_id INT NOT NULL,
                        order_date DATE NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL,

                        CONSTRAINT fk_customer
                            FOREIGN KEY(customer_id)
                                REFERENCES customers(id)
);
CREATE TABLE order_details (
                               order_id INT,
                               product_id INT,
                               quantity INT NOT NULL,

                               PRIMARY KEY(order_id, product_id),

                               FOREIGN KEY(order_id)
                                   REFERENCES orders(id),

                               FOREIGN KEY(product_id)
                                   REFERENCES products(id)
);