CREATE TABLE customers_orders_details (
                                          id BIGINT NOT NULL AUTO_INCREMENT,
                                          customer_id BIGINT NOT NULL,
                                          order_details_id BIGINT NOT NULL,
                                          PRIMARY KEY (id),
                                          FOREIGN KEY (customer_id) REFERENCES customers(id),
                                          FOREIGN KEY (order_details_id) REFERENCES orders_details(id)
);
