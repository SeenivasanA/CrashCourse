
CREATE TABLE `users`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email`        VARCHAR(256),
    `firstname`    VARCHAR(256),
    `lastname`     VARCHAR(256),
    `password`     VARCHAR(1000),
    `role`         VARCHAR(256),
    PRIMARY KEY (`id`)
);