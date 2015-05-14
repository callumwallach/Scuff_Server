CREATE DATABASE  IF NOT EXISTS `scuff` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scuff`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: scuff
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `driver_journeys`
--

DROP TABLE IF EXISTS `driver_journeys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver_journeys` (
  `DriverId` bigint(20) NOT NULL,
  `JourneyId` varchar(255) NOT NULL,
  PRIMARY KEY (`DriverId`,`JourneyId`),
  KEY `FK_8dbu674ogkyqcj1gx35orwa8g` (`JourneyId`),
  KEY `FK_4xkn1f18i7c39bkoxtadwup5r` (`DriverId`),
  CONSTRAINT `FK_4xkn1f18i7c39bkoxtadwup5r` FOREIGN KEY (`DriverId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_8dbu674ogkyqcj1gx35orwa8g` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `driver_routes`
--

DROP TABLE IF EXISTS `driver_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver_routes` (
  `DriverId` bigint(20) NOT NULL,
  `RouteId` bigint(20) NOT NULL,
  PRIMARY KEY (`DriverId`,`RouteId`),
  KEY `FK_j132uv65265k4kigrvl429kf8` (`RouteId`),
  KEY `FK_n2kpjwemc3nro61u98su3xr2n` (`DriverId`),
  CONSTRAINT `FK_n2kpjwemc3nro61u98su3xr2n` FOREIGN KEY (`DriverId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_j132uv65265k4kigrvl429kf8` FOREIGN KEY (`RouteId`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `journey`
--

DROP TABLE IF EXISTS `journey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey` (
  `JourneyId` varchar(255) NOT NULL,
  `AppId` varchar(255) NOT NULL,
  `Completed` datetime DEFAULT NULL,
  `Created` datetime NOT NULL,
  `Source` varchar(255) NOT NULL,
  `State` varchar(255) NOT NULL,
  `TotalDistance` float NOT NULL,
  `TotalDuration` bigint(20) NOT NULL,
  `driver` bigint(20) NOT NULL,
  `route` bigint(20) NOT NULL,
  `school` bigint(20) NOT NULL,
  PRIMARY KEY (`JourneyId`),
  KEY `FK_324idgrphricmey5qxah1uafo` (`driver`),
  KEY `FK_g1170354s1mt2x3yf7ye90knd` (`route`),
  KEY `FK_t5krvragtj64jy6yvde27cj75` (`school`),
  CONSTRAINT `FK_t5krvragtj64jy6yvde27cj75` FOREIGN KEY (`school`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_324idgrphricmey5qxah1uafo` FOREIGN KEY (`driver`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_g1170354s1mt2x3yf7ye90knd` FOREIGN KEY (`route`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `journey_tickets`
--

DROP TABLE IF EXISTS `journey_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey_tickets` (
  `JourneyId` varchar(255) NOT NULL,
  `TicketId` varchar(255) NOT NULL,
  PRIMARY KEY (`JourneyId`,`TicketId`),
  UNIQUE KEY `UK_otbf3mqgr6vsgv872ecxciawp` (`TicketId`),
  KEY `FK_otbf3mqgr6vsgv872ecxciawp` (`TicketId`),
  KEY `FK_plq6hrgcvig1561n934sqa06s` (`JourneyId`),
  CONSTRAINT `FK_plq6hrgcvig1561n934sqa06s` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`),
  CONSTRAINT `FK_otbf3mqgr6vsgv872ecxciawp` FOREIGN KEY (`TicketId`) REFERENCES `ticket` (`TicketId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parents_children`
--

DROP TABLE IF EXISTS `parents_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parents_children` (
  `ParentId` bigint(20) NOT NULL,
  `ChildId` bigint(20) NOT NULL,
  PRIMARY KEY (`ParentId`,`ChildId`),
  KEY `FK_e591ajiwlelo30li1xgxatv0o` (`ChildId`),
  KEY `FK_rl365hd6w6nyjw0rx3vcvk9u` (`ParentId`),
  CONSTRAINT `FK_rl365hd6w6nyjw0rx3vcvk9u` FOREIGN KEY (`ParentId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_e591ajiwlelo30li1xgxatv0o` FOREIGN KEY (`ChildId`) REFERENCES `person` (`PersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `passenger_routes`
--

DROP TABLE IF EXISTS `passenger_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passenger_routes` (
  `PassengerId` bigint(20) NOT NULL,
  `RouteId` bigint(20) NOT NULL,
  PRIMARY KEY (`PassengerId`,`RouteId`),
  KEY `FK_nbs3wchb8i1vsvmxsu51et4la` (`RouteId`),
  KEY `FK_6g5nx9pw1eei9retum83bvsll` (`PassengerId`),
  CONSTRAINT `FK_6g5nx9pw1eei9retum83bvsll` FOREIGN KEY (`PassengerId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_nbs3wchb8i1vsvmxsu51et4la` FOREIGN KEY (`RouteId`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `passenger_tickets`
--

DROP TABLE IF EXISTS `passenger_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passenger_tickets` (
  `PassengerId` bigint(20) NOT NULL,
  `TicketId` varchar(255) NOT NULL,
  PRIMARY KEY (`PassengerId`,`TicketId`),
  UNIQUE KEY `UK_813m2gtvsupb66yjs39rm1qlb` (`TicketId`),
  KEY `FK_813m2gtvsupb66yjs39rm1qlb` (`TicketId`),
  KEY `FK_byibonc4r9jhuqpdfifb5fmhb` (`PassengerId`),
  CONSTRAINT `FK_byibonc4r9jhuqpdfifb5fmhb` FOREIGN KEY (`PassengerId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_813m2gtvsupb66yjs39rm1qlb` FOREIGN KEY (`TicketId`) REFERENCES `ticket` (`TicketId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `DTYPE` varchar(31) NOT NULL,
  `PersonId` bigint(20) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(255) NOT NULL,
  `Gender` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `MiddleName` varchar(255) DEFAULT NULL,
  `Picture` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `RouteId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `RouteMap` varchar(255) DEFAULT NULL,
  `school` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`RouteId`),
  KEY `FK_2wbt52tdcporxn42v86xgg8o` (`school`),
  CONSTRAINT `FK_2wbt52tdcporxn42v86xgg8o` FOREIGN KEY (`school`) REFERENCES `school` (`SchoolId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
  `SchoolId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Altitude` double DEFAULT NULL,
  `Latitude` double DEFAULT NULL,
  `Longitude` double DEFAULT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`SchoolId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_children`
--

DROP TABLE IF EXISTS `school_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_children` (
  `SchoolId` bigint(20) NOT NULL,
  `ChildId` bigint(20) NOT NULL,
  PRIMARY KEY (`SchoolId`,`ChildId`),
  KEY `FK_4yan0lna1c97gddpsan8xeehb` (`ChildId`),
  KEY `FK_nd9umedgq0ieswuotbqqdd77l` (`SchoolId`),
  CONSTRAINT `FK_nd9umedgq0ieswuotbqqdd77l` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_4yan0lna1c97gddpsan8xeehb` FOREIGN KEY (`ChildId`) REFERENCES `person` (`PersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_drivers`
--

DROP TABLE IF EXISTS `school_drivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_drivers` (
  `SchoolId` bigint(20) NOT NULL,
  `DriverId` bigint(20) NOT NULL,
  PRIMARY KEY (`SchoolId`,`DriverId`),
  KEY `FK_1vaq4et3se0o2incdljrmrrtv` (`DriverId`),
  KEY `FK_lyff79c65wm2hbwt1bytqh3ym` (`SchoolId`),
  CONSTRAINT `FK_lyff79c65wm2hbwt1bytqh3ym` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_1vaq4et3se0o2incdljrmrrtv` FOREIGN KEY (`DriverId`) REFERENCES `person` (`PersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_journeys`
--

DROP TABLE IF EXISTS `school_journeys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_journeys` (
  `SchoolId` bigint(20) NOT NULL,
  `JourneyId` varchar(255) NOT NULL,
  PRIMARY KEY (`SchoolId`,`JourneyId`),
  KEY `FK_renvmuardt9f3q82kols76hki` (`JourneyId`),
  KEY `FK_8vbobsyeuxx91d85potety33j` (`SchoolId`),
  CONSTRAINT `FK_8vbobsyeuxx91d85potety33j` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_renvmuardt9f3q82kols76hki` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_routes`
--

DROP TABLE IF EXISTS `school_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_routes` (
  `SchoolId` bigint(20) NOT NULL,
  `RouteId` bigint(20) NOT NULL,
  PRIMARY KEY (`SchoolId`,`RouteId`),
  UNIQUE KEY `UK_ip03jssj83u4tn5q3jpi77aii` (`RouteId`),
  KEY `FK_ip03jssj83u4tn5q3jpi77aii` (`RouteId`),
  KEY `FK_iunpkpgtvdqcbmh2lb8dyoavk` (`SchoolId`),
  CONSTRAINT `FK_iunpkpgtvdqcbmh2lb8dyoavk` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_ip03jssj83u4tn5q3jpi77aii` FOREIGN KEY (`RouteId`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `TicketId` varchar(255) NOT NULL,
  `IssueDate` datetime DEFAULT NULL,
  `Latitude` double DEFAULT NULL,
  `Longitude` bigint(20) DEFAULT NULL,
  `StampDate` datetime DEFAULT NULL,
  `Journey` varchar(255) DEFAULT NULL,
  `Passenger` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TicketId`),
  KEY `FK_d4nubepam37xw5277wopr6q1w` (`Journey`),
  KEY `FK_qld062xlclu7bpe22s9o0qs57` (`Passenger`),
  CONSTRAINT `FK_qld062xlclu7bpe22s9o0qs57` FOREIGN KEY (`Passenger`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_d4nubepam37xw5277wopr6q1w` FOREIGN KEY (`Journey`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `waypoint`
--

DROP TABLE IF EXISTS `waypoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `waypoint` (
  `WaypointId` varchar(255) NOT NULL,
  `Accuracy` float NOT NULL,
  `Altitude` double NOT NULL,
  `Bearing` float NOT NULL,
  `Created` datetime NOT NULL,
  `Distance` float NOT NULL,
  `Duration` bigint(20) NOT NULL,
  `Latitude` double NOT NULL,
  `Longitude` double NOT NULL,
  `Provider` varchar(255) NOT NULL,
  `Speed` float NOT NULL,
  `State` varchar(255) NOT NULL,
  `JourneyId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`WaypointId`),
  KEY `FK_mai6nwhqjicrrvetj5gabla38` (`JourneyId`),
  CONSTRAINT `FK_mai6nwhqjicrrvetj5gabla38` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-15  1:42:03
