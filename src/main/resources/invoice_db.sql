USE `invoicedb`;
CREATE TABLE `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id_ukey` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) unsigned NOT NULL,
  `street` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(10) NOT NULL,
  `country` varchar(50) NOT NULL,
  `zip_code` int(7) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `address_ukey` (`street`,`city`,`state`),
  KEY `customer_id_fkey` (`customer_id`),
  CONSTRAINT `customer_id_fkey` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` float(8,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_ukey` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `invoice` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `invoice_customer_id_fkey` (`customer_id`),
  CONSTRAINT `invoice_customer_id_fkey` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `line_items` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int(11) unsigned NOT NULL,
  `invoice_id` int(11) unsigned NOT NULL,
  `quantity` int(11) NOT NULL,
  `amount` float(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id_fkey` (`product_id`),
  KEY `product_invoice_id_fkey` (`invoice_id`),
  CONSTRAINT `product_id_fkey` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_invoice_id_fkey` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `email_sent` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_id` int(11) unsigned NOT NULL,
  `customer_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email_sent_invoice_id_fkey` (`invoice_id`),
  KEY `email_sent_customer_id_fkey` (`customer_id`),
  CONSTRAINT `email_sent_customer_id_fkey` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `email_sent_invoice_id_fkey` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `product` (`name`, `price`) values ("ProductA", "100.00");
INSERT INTO `product` (`name`, `price`) values ("ProductB", "150.00");