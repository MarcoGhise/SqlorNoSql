CREATE DATABASE IF NOT EXISTS store;

use store;
DROP TABLE IF EXISTS `Product`;
DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS `Basket`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Catalog`;


CREATE TABLE `Catalog` (
  `productId` varchar(36) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `price` double,
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `User` (
  `userId` varchar(36) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Basket` (
  `basketId` varchar(36) NOT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `orderId` varchar(36) DEFAULT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`basketId`),
  KEY `Basket_FK` (`userId`),
  CONSTRAINT `Basket_FK` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Product` (
  `productId` varchar(36) NOT NULL,
  `basketId` varchar(36) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`productId`,`basketId`),
  CONSTRAINT `Product_FK_1` FOREIGN KEY (`productId`) REFERENCES `Catalog` (`productId`),
  CONSTRAINT `Product_FK_2` FOREIGN KEY (`basketId`) REFERENCES `Basket` (`basketId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Order` (
  `orderId` varchar(36) NOT NULL,
  `basketId` varchar(36) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderId`),
  KEY `Order_FK_1` (`basketId`),
  CONSTRAINT `Order_FK_1` FOREIGN KEY (`basketId`) REFERENCES `Basket` (`basketId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `Catalog` WRITE;
/*!40000 ALTER TABLE `Catalog` DISABLE KEYS */;
INSERT INTO `Catalog` VALUES ('0db3401f-3085-47c6-903a-8015d17a803e','APPLARO','3-seat modular sofa, outdoor, with footstool brown',300),('9caf0ffc-a945-4551-bc6c-00ad6e20caaf','BORRBY','Lantern for block candle, in/outdoor white',14),('d8a03eca-53af-4dee-a4e4-19081caa3ad6','SVARTHO','Cushion cover, beige',17);
/*!40000 ALTER TABLE `Catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--




