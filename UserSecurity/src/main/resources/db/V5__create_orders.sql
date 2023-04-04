CREATE TABLE `orders`
(
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT,
    `ordered_date`      DATE,
    `comments`          VARCHAR(256),
    `price`             BIGINT(10),
    `status`            VARCHAR(256),
    `customer_id`       BIGINT(20),
    `shipped_date`      DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    PRIMARY KEY (`id`)
);
