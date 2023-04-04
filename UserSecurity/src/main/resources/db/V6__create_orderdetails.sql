
CREATE TABLE `orders_details`
(
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT,
    `product_code`      INT,
    `quantity_ordered`       INT,
    `price`             BIGINT(10),
    `order_line_number`            INT,
    PRIMARY KEY (`id`)
);