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
  `DriverId` varchar(255) NOT NULL,
  `RouteId` varchar(255) NOT NULL,
  `SchoolId` varchar(255) NOT NULL,
  `Source` varchar(255) NOT NULL,
  `State` varchar(255) NOT NULL,
  `TotalDistance` float NOT NULL,
  `TotalDuration` bigint(20) NOT NULL,
  PRIMARY KEY (`JourneyId`)
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
-- Dumping data for table `parents_children`
--

LOCK TABLES `parents_children` WRITE;
/*!40000 ALTER TABLE `parents_children` DISABLE KEYS */;
INSERT INTO `parents_children` VALUES (4,1),(5,1),(4,2),(5,2),(4,3),(5,3);
/*!40000 ALTER TABLE `parents_children` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parents_routes`
--

DROP TABLE IF EXISTS `parents_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parents_routes` (
  `ParentId` bigint(20) NOT NULL,
  `RouteId` bigint(20) NOT NULL,
  PRIMARY KEY (`ParentId`,`RouteId`),
  KEY `FK_2oamtqpk5hiabc75ri695ytqh` (`RouteId`),
  KEY `FK_r6bratlkbot6iqh0e0ivq35dg` (`ParentId`),
  CONSTRAINT `FK_r6bratlkbot6iqh0e0ivq35dg` FOREIGN KEY (`ParentId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `FK_2oamtqpk5hiabc75ri695ytqh` FOREIGN KEY (`RouteId`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parents_routes`
--

LOCK TABLES `parents_routes` WRITE;
/*!40000 ALTER TABLE `parents_routes` DISABLE KEYS */;
INSERT INTO `parents_routes` VALUES (4,1),(5,1),(5,2);
/*!40000 ALTER TABLE `parents_routes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('Child',1,'Cayden','MALE','Lin-Vaile',NULL,NULL,NULL,NULL),('Child',2,'Connor','MALE','Lin',NULL,NULL,NULL,NULL),('Child',3,'Mia','FEMALE','Lin',NULL,NULL,NULL,NULL),('Parent',4,'Christine','FEMALE','Lin',NULL,NULL,'christine@gmail.com','021666377'),('Parent',5,'Callum','MALE','Wallach',NULL,NULL,'callum@gmail.com','021658093');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`RouteId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,'Long Drive','longdrive.png'),(2,'St Heliers Bay','stheliersbayroad.png'),(3,'Riddell Nth','riddellroadnorth.png'),(4,'Riddell Sth','riddellroadsouth.png');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES (1,42.16,-36.858255,174.860823,'St Heliers School');
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `FK_ip03jssj83u4tn5q3jpi77aii` (`RouteId`),
  KEY `FK_iunpkpgtvdqcbmh2lb8dyoavk` (`SchoolId`),
  CONSTRAINT `FK_iunpkpgtvdqcbmh2lb8dyoavk` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_ip03jssj83u4tn5q3jpi77aii` FOREIGN KEY (`RouteId`) REFERENCES `route` (`RouteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_routes`
--

LOCK TABLES `school_routes` WRITE;
/*!40000 ALTER TABLE `school_routes` DISABLE KEYS */;
INSERT INTO `school_routes` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `school_routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schools_children`
--

DROP TABLE IF EXISTS `schools_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schools_children` (
  `SchoolId` bigint(20) NOT NULL,
  `ChildId` bigint(20) NOT NULL,
  PRIMARY KEY (`SchoolId`,`ChildId`),
  KEY `FK_jkjgta5xedvg072kc9ab9nfgu` (`ChildId`),
  KEY `FK_44lgpx02j9b2qtw786jelniid` (`SchoolId`),
  CONSTRAINT `FK_44lgpx02j9b2qtw786jelniid` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_jkjgta5xedvg072kc9ab9nfgu` FOREIGN KEY (`ChildId`) REFERENCES `person` (`PersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schools_children`
--

LOCK TABLES `schools_children` WRITE;
/*!40000 ALTER TABLE `schools_children` DISABLE KEYS */;
INSERT INTO `schools_children` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `schools_children` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schools_parents`
--

DROP TABLE IF EXISTS `schools_parents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schools_parents` (
  `SchoolId` bigint(20) NOT NULL,
  `ParentId` bigint(20) NOT NULL,
  PRIMARY KEY (`SchoolId`,`ParentId`),
  KEY `FK_audak8u9t43xocr651yt4hwfx` (`ParentId`),
  KEY `FK_t1a3qscunk1i0qywiebg0s5p0` (`SchoolId`),
  CONSTRAINT `FK_t1a3qscunk1i0qywiebg0s5p0` FOREIGN KEY (`SchoolId`) REFERENCES `school` (`SchoolId`),
  CONSTRAINT `FK_audak8u9t43xocr651yt4hwfx` FOREIGN KEY (`ParentId`) REFERENCES `person` (`PersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schools_parents`
--

LOCK TABLES `schools_parents` WRITE;
/*!40000 ALTER TABLE `schools_parents` DISABLE KEYS */;
INSERT INTO `schools_parents` VALUES (1,4),(1,5);
/*!40000 ALTER TABLE `schools_parents` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2015-05-05 14:18:17
