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
-- Table structure for table `adult_children`
--

DROP TABLE IF EXISTS `adult_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adult_children` (
  `AdultId` bigint(20) NOT NULL,
  `ChildId` bigint(20) NOT NULL,
  PRIMARY KEY (`AdultId`,`ChildId`),
  KEY `FK_mtp9fy7sv6j74tw2askvu3qvh` (`ChildId`),
  KEY `FK_hxbju79rjq6chi9i715yf2ov4` (`AdultId`),
  CONSTRAINT `FK_hxbju79rjq6chi9i715yf2ov4` FOREIGN KEY (`AdultId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_mtp9fy7sv6j74tw2askvu3qvh` FOREIGN KEY (`ChildId`) REFERENCES `child` (`ChildId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adult_children`
--

LOCK TABLES `adult_children` WRITE;
/*!40000 ALTER TABLE `adult_children` DISABLE KEYS */;
INSERT INTO `adult_children` VALUES (4,1),(5,1),(4,2),(5,2),(4,3),(5,3),(6,4);
/*!40000 ALTER TABLE `adult_children` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adult_guidees`
--

DROP TABLE IF EXISTS `adult_guidees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adult_guidees` (
  `AdultId` bigint(20) NOT NULL,
  `InstitutionId` bigint(20) NOT NULL,
  PRIMARY KEY (`AdultId`,`InstitutionId`),
  KEY `FK_5jttyb8fcs7rwpi89owxheu7v` (`InstitutionId`),
  KEY `FK_s2kd46uvs07ltc8v3uy8aul2w` (`AdultId`),
  CONSTRAINT `FK_s2kd46uvs07ltc8v3uy8aul2w` FOREIGN KEY (`AdultId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_5jttyb8fcs7rwpi89owxheu7v` FOREIGN KEY (`InstitutionId`) REFERENCES `coordinator` (`CoordinatorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adult_guidees`
--

LOCK TABLES `adult_guidees` WRITE;
/*!40000 ALTER TABLE `adult_guidees` DISABLE KEYS */;
INSERT INTO `adult_guidees` VALUES (4,1),(5,1);
/*!40000 ALTER TABLE `adult_guidees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `child`
--

DROP TABLE IF EXISTS `child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `child` (
  `ChildId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Active` tinyint(1) DEFAULT NULL,
  `LastModified` datetime NOT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `Gender` int(11) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `MiddleName` varchar(255) DEFAULT NULL,
  `Picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ChildId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `child`
--

LOCK TABLES `child` WRITE;
/*!40000 ALTER TABLE `child` DISABLE KEYS */;
INSERT INTO `child` VALUES (1,1,'2015-06-29 16:31:19','Cayden',0,'Lin-Vaile',NULL,NULL),(2,1,'2015-06-29 16:31:19','Connor',0,'Lin',NULL,NULL),(3,1,'2015-06-29 16:31:19','Mia',1,'Lin',NULL,NULL),(4,1,'2015-06-29 16:31:19','Jack',0,'Secondsong',NULL,NULL);
/*!40000 ALTER TABLE `child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator`
--

DROP TABLE IF EXISTS `coordinator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator` (
  `DTYPE` varchar(31) NOT NULL,
  `CoordinatorId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Active` tinyint(1) DEFAULT NULL,
  `LastModified` datetime NOT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `Gender` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `MiddleName` varchar(255) DEFAULT NULL,
  `Picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CoordinatorId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator`
--

LOCK TABLES `coordinator` WRITE;
/*!40000 ALTER TABLE `coordinator` DISABLE KEYS */;
INSERT INTO `coordinator` VALUES ('Institution',1,1,'2015-06-29 16:31:19','2015-06-29 16:31:19','info@stheliersschool.govt.nz','St Heliers School','09 6578893',NULL,NULL,NULL,NULL,NULL),('Institution',2,1,'2015-06-29 16:31:19','2015-06-29 16:31:19','info@rugby.org.nz','Rugby Club','09 664773',NULL,NULL,NULL,NULL,NULL),('Institution',3,1,'2015-06-29 16:31:19','2015-06-29 16:31:19','info@soccer.org.nz','Soccer Club','09 7765534',NULL,NULL,NULL,NULL,NULL),('Adult',4,1,'2015-06-29 16:31:19','2015-06-29 16:31:19','callum@gmail.com',NULL,'021658093','Callum','MALE','Wallach',NULL,NULL),('Adult',5,1,'2015-06-29 16:31:19','2015-06-29 16:31:19','christine@gmail.com',NULL,'021666377','Christine','FEMALE','Lin',NULL,NULL),('Adult',6,1,'2015-06-29 16:31:19','2015-06-29 16:31:19','steve@gmail.com',NULL,'021667885','Steve','MALE','Secondsong',NULL,NULL);
/*!40000 ALTER TABLE `coordinator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator_currentjourneys`
--

DROP TABLE IF EXISTS `coordinator_currentjourneys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator_currentjourneys` (
  `CoordinatorId` bigint(20) NOT NULL,
  `JourneyId` bigint(20) NOT NULL,
  PRIMARY KEY (`CoordinatorId`,`JourneyId`),
  KEY `FK_k8tlv45l3w71n92c7b1bfi50u` (`JourneyId`),
  KEY `FK_5mr9s5403xglavb9h1w01clod` (`CoordinatorId`),
  CONSTRAINT `FK_5mr9s5403xglavb9h1w01clod` FOREIGN KEY (`CoordinatorId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_k8tlv45l3w71n92c7b1bfi50u` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator_currentjourneys`
--

LOCK TABLES `coordinator_currentjourneys` WRITE;
/*!40000 ALTER TABLE `coordinator_currentjourneys` DISABLE KEYS */;
/*!40000 ALTER TABLE `coordinator_currentjourneys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator_friends`
--

DROP TABLE IF EXISTS `coordinator_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator_friends` (
  `ThisFriendId` bigint(20) NOT NULL,
  `ThatFriendId` bigint(20) NOT NULL,
  PRIMARY KEY (`ThisFriendId`,`ThatFriendId`),
  KEY `FK_ltnpt1cr95vndsw8xmv1kqbxw` (`ThatFriendId`),
  KEY `FK_sgu6um11ytrkugoaed3vq6u92` (`ThisFriendId`),
  CONSTRAINT `FK_sgu6um11ytrkugoaed3vq6u92` FOREIGN KEY (`ThisFriendId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_ltnpt1cr95vndsw8xmv1kqbxw` FOREIGN KEY (`ThatFriendId`) REFERENCES `coordinator` (`CoordinatorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator_friends`
--

LOCK TABLES `coordinator_friends` WRITE;
/*!40000 ALTER TABLE `coordinator_friends` DISABLE KEYS */;
INSERT INTO `coordinator_friends` VALUES (4,1),(5,1),(6,1),(6,2),(5,3);
/*!40000 ALTER TABLE `coordinator_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator_pastjourneys`
--

DROP TABLE IF EXISTS `coordinator_pastjourneys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator_pastjourneys` (
  `CoordinatorId` bigint(20) NOT NULL,
  `JourneyId` bigint(20) NOT NULL,
  PRIMARY KEY (`CoordinatorId`,`JourneyId`),
  KEY `FK_mm8hfunh6pry8gcfg3l45x5k2` (`JourneyId`),
  KEY `FK_665f03ofqd6yaikyj1pmc4pf6` (`CoordinatorId`),
  CONSTRAINT `FK_665f03ofqd6yaikyj1pmc4pf6` FOREIGN KEY (`CoordinatorId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_mm8hfunh6pry8gcfg3l45x5k2` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator_pastjourneys`
--

LOCK TABLES `coordinator_pastjourneys` WRITE;
/*!40000 ALTER TABLE `coordinator_pastjourneys` DISABLE KEYS */;
/*!40000 ALTER TABLE `coordinator_pastjourneys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator_places`
--

DROP TABLE IF EXISTS `coordinator_places`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator_places` (
  `CoordinatorId` bigint(20) NOT NULL,
  `PlaceId` bigint(20) NOT NULL,
  PRIMARY KEY (`CoordinatorId`,`PlaceId`),
  KEY `FK_prll506fj01p26k3ewm1311cs` (`PlaceId`),
  KEY `FK_7bb32ao8tt2hotnctmim0v445` (`CoordinatorId`),
  CONSTRAINT `FK_7bb32ao8tt2hotnctmim0v445` FOREIGN KEY (`CoordinatorId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_prll506fj01p26k3ewm1311cs` FOREIGN KEY (`PlaceId`) REFERENCES `place` (`PlaceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator_places`
--

LOCK TABLES `coordinator_places` WRITE;
/*!40000 ALTER TABLE `coordinator_places` DISABLE KEYS */;
INSERT INTO `coordinator_places` VALUES (1,1),(1,2),(1,3),(1,4),(4,5),(5,6),(6,7),(3,8),(2,9);
/*!40000 ALTER TABLE `coordinator_places` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator_routes`
--

DROP TABLE IF EXISTS `coordinator_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator_routes` (
  `CoordinatorId` bigint(20) NOT NULL,
  `RouteId` bigint(20) NOT NULL,
  PRIMARY KEY (`CoordinatorId`,`RouteId`),
  UNIQUE KEY `UK_n49hjdjvk0iwmri7ftvrg8rhe` (`RouteId`),
  KEY `FK_n49hjdjvk0iwmri7ftvrg8rhe` (`RouteId`),
  KEY `FK_mwdntqgd795q96tabc14lv77p` (`CoordinatorId`),
  CONSTRAINT `FK_mwdntqgd795q96tabc14lv77p` FOREIGN KEY (`CoordinatorId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_n49hjdjvk0iwmri7ftvrg8rhe` FOREIGN KEY (`RouteId`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator_routes`
--

LOCK TABLES `coordinator_routes` WRITE;
/*!40000 ALTER TABLE `coordinator_routes` DISABLE KEYS */;
INSERT INTO `coordinator_routes` VALUES (1,1),(1,2),(1,3),(4,4),(5,5),(6,6);
/*!40000 ALTER TABLE `coordinator_routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institution_guides`
--

DROP TABLE IF EXISTS `institution_guides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institution_guides` (
  `InstitutionId` bigint(20) NOT NULL,
  `GuideId` bigint(20) NOT NULL,
  PRIMARY KEY (`InstitutionId`,`GuideId`),
  KEY `FK_juouk9jknws1ogvv14q60b8e` (`GuideId`),
  KEY `FK_qfo5elrmp7fh9xjnf0v5xdxcw` (`InstitutionId`),
  CONSTRAINT `FK_qfo5elrmp7fh9xjnf0v5xdxcw` FOREIGN KEY (`InstitutionId`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_juouk9jknws1ogvv14q60b8e` FOREIGN KEY (`GuideId`) REFERENCES `coordinator` (`CoordinatorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institution_guides`
--

LOCK TABLES `institution_guides` WRITE;
/*!40000 ALTER TABLE `institution_guides` DISABLE KEYS */;
INSERT INTO `institution_guides` VALUES (1,4);
/*!40000 ALTER TABLE `institution_guides` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journey`
--

DROP TABLE IF EXISTS `journey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey` (
  `JourneyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Active` tinyint(1) DEFAULT NULL,
  `LastModified` datetime NOT NULL,
  `AppId` varchar(255) NOT NULL,
  `Completed` datetime DEFAULT NULL,
  `Created` datetime NOT NULL,
  `Source` varchar(255) NOT NULL,
  `State` varchar(255) NOT NULL,
  `TotalDistance` float NOT NULL,
  `TotalDuration` bigint(20) NOT NULL,
  `Agent` bigint(20) NOT NULL,
  `Destination` bigint(20) NOT NULL,
  `Guide` bigint(20) NOT NULL,
  `Origin` bigint(20) NOT NULL,
  `Owner` bigint(20) NOT NULL,
  `Route` bigint(20) NOT NULL,
  PRIMARY KEY (`JourneyId`),
  KEY `FK_22f39ccnve53x4gnld56ivd1o` (`Agent`),
  KEY `FK_c56y7fvfqi9ytr3k7a9wo5g64` (`Destination`),
  KEY `FK_9si8ychpomg6cypfpvawuowk7` (`Guide`),
  KEY `FK_jedy3uwqmr6kgabxgcdvorgpk` (`Origin`),
  KEY `FK_jojlbe9hfvjcmkwehm9fae88p` (`Owner`),
  KEY `FK_8xunvbb84lpx8691ldciuk0ud` (`Route`),
  CONSTRAINT `FK_8xunvbb84lpx8691ldciuk0ud` FOREIGN KEY (`Route`) REFERENCES `route` (`RouteId`),
  CONSTRAINT `FK_22f39ccnve53x4gnld56ivd1o` FOREIGN KEY (`Agent`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_9si8ychpomg6cypfpvawuowk7` FOREIGN KEY (`Guide`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_c56y7fvfqi9ytr3k7a9wo5g64` FOREIGN KEY (`Destination`) REFERENCES `place` (`PlaceId`),
  CONSTRAINT `FK_jedy3uwqmr6kgabxgcdvorgpk` FOREIGN KEY (`Origin`) REFERENCES `place` (`PlaceId`),
  CONSTRAINT `FK_jojlbe9hfvjcmkwehm9fae88p` FOREIGN KEY (`Owner`) REFERENCES `coordinator` (`CoordinatorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journey`
--

LOCK TABLES `journey` WRITE;
/*!40000 ALTER TABLE `journey` DISABLE KEYS */;
/*!40000 ALTER TABLE `journey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journey_issuedtickets`
--

DROP TABLE IF EXISTS `journey_issuedtickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey_issuedtickets` (
  `JourneyId` bigint(20) NOT NULL,
  `TicketId` bigint(20) NOT NULL,
  PRIMARY KEY (`JourneyId`,`TicketId`),
  UNIQUE KEY `UK_qve0dxtlvma5ljk770ru7bdqn` (`TicketId`),
  KEY `FK_qve0dxtlvma5ljk770ru7bdqn` (`TicketId`),
  KEY `FK_91iowqv413bmghrjo1b619ic2` (`JourneyId`),
  CONSTRAINT `FK_91iowqv413bmghrjo1b619ic2` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`),
  CONSTRAINT `FK_qve0dxtlvma5ljk770ru7bdqn` FOREIGN KEY (`TicketId`) REFERENCES `ticket` (`TicketId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journey_issuedtickets`
--

LOCK TABLES `journey_issuedtickets` WRITE;
/*!40000 ALTER TABLE `journey_issuedtickets` DISABLE KEYS */;
/*!40000 ALTER TABLE `journey_issuedtickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journey_stampedtickets`
--

DROP TABLE IF EXISTS `journey_stampedtickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey_stampedtickets` (
  `JourneyId` bigint(20) NOT NULL,
  `TicketId` bigint(20) NOT NULL,
  PRIMARY KEY (`JourneyId`,`TicketId`),
  UNIQUE KEY `UK_508rhoba0lyu5h8wehb10ask1` (`TicketId`),
  KEY `FK_508rhoba0lyu5h8wehb10ask1` (`TicketId`),
  KEY `FK_79orqhcijdts7xrw17p97k56t` (`JourneyId`),
  CONSTRAINT `FK_79orqhcijdts7xrw17p97k56t` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`),
  CONSTRAINT `FK_508rhoba0lyu5h8wehb10ask1` FOREIGN KEY (`TicketId`) REFERENCES `ticket` (`TicketId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journey_stampedtickets`
--

LOCK TABLES `journey_stampedtickets` WRITE;
/*!40000 ALTER TABLE `journey_stampedtickets` DISABLE KEYS */;
/*!40000 ALTER TABLE `journey_stampedtickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `place`
--

DROP TABLE IF EXISTS `place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `place` (
  `PlaceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Active` tinyint(1) DEFAULT NULL,
  `LastModified` datetime NOT NULL,
  `Altitude` double NOT NULL,
  `Latitude` double NOT NULL,
  `Longitude` double NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`PlaceId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` VALUES (1,1,'2015-06-29 16:31:19',42.16,-36.858255,174.860823,'St Heliers School South Gate'),(2,1,'2015-06-29 16:31:19',0,-36.85087,174.851444,'Long Drive Start'),(3,1,'2015-06-29 16:31:19',0,-36.851951,174.858268,'St Heliers Bay Road Start'),(4,1,'2015-06-29 16:31:19',0,-36.860039,174.868348,'Riddell Road Start'),(5,1,'2015-06-29 16:31:19',0,-36.860039,174.868348,'29 Tarawera Tce'),(6,1,'2015-06-29 16:31:19',0,-36.860039,174.868348,'29 Tarawera Tce'),(7,1,'2015-06-29 16:31:19',0,-36.860239,174.868348,'5 Bonny Tce'),(8,1,'2015-06-29 16:31:19',0,-36.860139,174.868348,'33 Long Dr'),(9,1,'2015-06-29 16:31:19',0,-36.860239,174.868348,'1 Scenic Dr');
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `RouteId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Active` tinyint(1) DEFAULT NULL,
  `LastModified` datetime NOT NULL,
  `Name` varchar(255) NOT NULL,
  `RouteMap` varchar(255) NOT NULL,
  `Destination` bigint(20) NOT NULL,
  `Origin` bigint(20) NOT NULL,
  `Owner` bigint(20) NOT NULL,
  PRIMARY KEY (`RouteId`),
  KEY `FK_ern9enuyi5abs57ti0co4lpg7` (`Destination`),
  KEY `FK_rt9dju8y6nj4i52e4cuvmhv77` (`Origin`),
  KEY `FK_dla590d7sdyrbcip6f6ikhjet` (`Owner`),
  CONSTRAINT `FK_dla590d7sdyrbcip6f6ikhjet` FOREIGN KEY (`Owner`) REFERENCES `coordinator` (`CoordinatorId`),
  CONSTRAINT `FK_ern9enuyi5abs57ti0co4lpg7` FOREIGN KEY (`Destination`) REFERENCES `place` (`PlaceId`),
  CONSTRAINT `FK_rt9dju8y6nj4i52e4cuvmhv77` FOREIGN KEY (`Origin`) REFERENCES `place` (`PlaceId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,1,'2015-06-29 16:31:19','Long Drive','longdrive.png',1,2,1),(2,1,'2015-06-29 16:31:19','St Heliers Bay Road','stheliersbayroad.png',1,3,1),(3,1,'2015-06-29 16:31:19','Riddell Road','riddellroadnorth.png',1,4,1),(4,1,'2015-06-29 16:31:19','To School','somemap.png',1,5,4),(5,1,'2015-06-29 16:31:19','To Soccer','somemap.png',8,6,5),(6,1,'2015-06-29 16:31:19','To Rugby','somemap.png',9,7,6);
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `TicketId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Active` tinyint(1) DEFAULT NULL,
  `LastModified` datetime NOT NULL,
  `IssueDate` datetime DEFAULT NULL,
  `Latitude` double DEFAULT NULL,
  `Longitude` double DEFAULT NULL,
  `StampDate` datetime DEFAULT NULL,
  `StampId` bigint(20) DEFAULT NULL,
  `State` int(11) DEFAULT NULL,
  `Child` bigint(20) DEFAULT NULL,
  `Journey` bigint(20) DEFAULT NULL,
  `ChildId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TicketId`),
  KEY `FK_3xrt2vi9snm6gmpofwhsae71s` (`Child`),
  KEY `FK_d4nubepam37xw5277wopr6q1w` (`Journey`),
  KEY `FK_ls6053hdctk06wqukdisnxope` (`ChildId`),
  CONSTRAINT `FK_ls6053hdctk06wqukdisnxope` FOREIGN KEY (`ChildId`) REFERENCES `child` (`ChildId`),
  CONSTRAINT `FK_3xrt2vi9snm6gmpofwhsae71s` FOREIGN KEY (`Child`) REFERENCES `child` (`ChildId`),
  CONSTRAINT `FK_d4nubepam37xw5277wopr6q1w` FOREIGN KEY (`Journey`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waypoint`
--

DROP TABLE IF EXISTS `waypoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `waypoint` (
  `WaypointId` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `JourneyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`WaypointId`),
  KEY `FK_mai6nwhqjicrrvetj5gabla38` (`JourneyId`),
  CONSTRAINT `FK_mai6nwhqjicrrvetj5gabla38` FOREIGN KEY (`JourneyId`) REFERENCES `journey` (`JourneyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waypoint`
--

LOCK TABLES `waypoint` WRITE;
/*!40000 ALTER TABLE `waypoint` DISABLE KEYS */;
/*!40000 ALTER TABLE `waypoint` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-29 16:31:23
