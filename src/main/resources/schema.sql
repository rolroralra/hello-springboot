CREATE TABLE IF NOT EXISTS `stock` (
    `product_id` varchar(36) NOT NULL,
    `amount` int NOT NULL,
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;