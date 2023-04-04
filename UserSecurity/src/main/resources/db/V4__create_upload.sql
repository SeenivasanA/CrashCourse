
CREATE TABLE `upload`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
    `file_name`    VARCHAR(256),
    `file_path`     VARCHAR(256),
    `file_type`     VARCHAR(1000),
    `file_data`     LONGBLOB,
    PRIMARY KEY (`id`)
);