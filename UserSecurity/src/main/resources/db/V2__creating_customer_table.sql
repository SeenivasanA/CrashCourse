
CREATE TABLE `customers`
(
    `id`                    BIGINT(20) NOT NULL AUTO_INCREMENT,
    `customer_email`         VARCHAR(256) NOT NULL UNIQUE,
    `first_name`             VARCHAR(256) NOT NULL,
    `last_name`              VARCHAR(256) NOT NULL,
    `customer_password`      VARCHAR(1000) NOT NULL,
    `customer_address`       VARCHAR(1000) NOT NULL,
    `customer_phone`         BIGINT(10) NOT NULL,
    `status`                 BOOLEAN DEFAULT 1,
    PRIMARY KEY (`id`)
);