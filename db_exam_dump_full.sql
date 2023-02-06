-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: db_exam
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Answer`
--

DROP TABLE IF EXISTS `Answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `choice_id` int DEFAULT NULL,
  `exam_session_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2viobqwt4kki5q04i51fox7ax` (`choice_id`),
  KEY `FK6qai0gis17l3ai0qrobr6q2kh` (`exam_session_id`),
  KEY `FKfiomvt17psxodcis3d8nmopx8` (`question_id`),
  CONSTRAINT `FK2viobqwt4kki5q04i51fox7ax` FOREIGN KEY (`choice_id`) REFERENCES `Choice` (`id`),
  CONSTRAINT `FK6qai0gis17l3ai0qrobr6q2kh` FOREIGN KEY (`exam_session_id`) REFERENCES `ExamSession` (`id`),
  CONSTRAINT `FKfiomvt17psxodcis3d8nmopx8` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Answer`
--

LOCK TABLES `Answer` WRITE;
/*!40000 ALTER TABLE `Answer` DISABLE KEYS */;
INSERT INTO `Answer` VALUES (1,12,1,4),(2,3,1,1),(3,5,1,2),(4,7,1,3),(5,15,1,5),(6,33,2,11),(7,37,2,13),(8,41,2,14),(9,45,2,15),(10,36,2,12),(11,28,3,10),(12,25,3,9),(13,23,3,8),(14,18,3,6),(15,20,3,7),(16,6,4,2),(17,7,4,3),(18,12,4,4),(19,1,4,1),(20,14,4,5),(21,38,5,13),(22,44,5,15),(23,31,5,11),(24,36,5,12),(25,40,5,14),(26,17,6,6),(27,23,6,8),(28,19,6,7),(29,28,6,10),(30,25,6,9),(31,2,7,1),(32,6,7,2),(33,9,7,3),(34,13,7,5),(35,12,7,4),(36,43,8,15),(37,31,8,11),(38,40,8,14),(39,36,8,12),(40,38,8,13),(41,20,9,7),(42,22,9,8),(43,18,9,6),(44,26,9,9),(45,28,9,10);
/*!40000 ALTER TABLE `Answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Choice`
--

DROP TABLE IF EXISTS `Choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Choice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `choice_text` varchar(255) DEFAULT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcqxpjlgi5gdl30yb78eicil1e` (`question_id`),
  CONSTRAINT `FKcqxpjlgi5gdl30yb78eicil1e` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Choice`
--

LOCK TABLES `Choice` WRITE;
/*!40000 ALTER TABLE `Choice` DISABLE KEYS */;
INSERT INTO `Choice` VALUES (1,'Canberra',_binary '',1),(2,'Sydney',_binary '\0',1),(3,'Melbourne',_binary '\0',1),(4,'Paris',_binary '',2),(5,'Lyon',_binary '\0',2),(6,'Marseille',_binary '\0',2),(7,'Berlin',_binary '',3),(8,'Hamburg',_binary '\0',3),(9,'Bonn',_binary '\0',3),(10,'Rome',_binary '',4),(11,'Milan',_binary '\0',4),(12,'Vatican',_binary '\0',4),(13,'Ottawa',_binary '',5),(14,'Toronto',_binary '\0',5),(15,'Quebec City',_binary '\0',5),(16,'17',_binary '',6),(17,'27',_binary '\0',6),(18,'16',_binary '\0',6),(19,'76',_binary '',7),(20,'75',_binary '\0',7),(21,'77',_binary '\0',7),(22,'110',_binary '',8),(23,'100',_binary '\0',8),(24,'111',_binary '\0',8),(25,'161',_binary '',9),(26,'171',_binary '\0',9),(27,'151',_binary '\0',9),(28,'146',_binary '',10),(29,'136',_binary '\0',10),(30,'156',_binary '\0',10),(31,'A database is optimized for transaction processing, while a data warehouse is optimized for querying and analysis.',_binary '',11),(32,'A database is a collection of data organized in a specific way, while a data warehouse is a large repository of data optimized for querying and analysis.',_binary '\0',11),(33,'A database is a flat file, while a data warehouse is a relational database.',_binary '\0',11),(34,'To filter incoming and outgoing network traffic based on predetermined security rules.',_binary '',12),(35,'To encrypt all network traffic.',_binary '\0',12),(36,'To prevent unauthorized access to a private network.',_binary '\0',12),(37,'As a REST API.',_binary '',13),(38,'As a standalone application.',_binary '\0',13),(39,'As a library integrated into other software.',_binary '\0',13),(40,'Increased flexibility compared to traditional on-premise solutions.',_binary '',14),(41,'Lower costs compared to traditional on-premise solutions.',_binary '\0',14),(42,'Increased security compared to traditional on-premise solutions.',_binary '\0',14),(43,'A iterative and incremental approach to software development that emphasizes collaboration and flexibility.',_binary '',15),(44,'A sequential approach to software development that emphasizes planning and design.',_binary '\0',15),(45,'A waterfall approach to software development that emphasizes documentation and testing.',_binary '\0',15);
/*!40000 ALTER TABLE `Choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Exam`
--

DROP TABLE IF EXISTS `Exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Exam` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tccgpumpwc7sb5pam58ulyib4` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Exam`
--

LOCK TABLES `Exam` WRITE;
/*!40000 ALTER TABLE `Exam` DISABLE KEYS */;
INSERT INTO `Exam` VALUES (1,'Geography: capital cities'),(3,'Information technologies'),(2,'Math: sum of two numbers');
/*!40000 ALTER TABLE `Exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExamSession`
--

DROP TABLE IF EXISTS `ExamSession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ExamSession` (
  `id` int NOT NULL AUTO_INCREMENT,
  `score` int NOT NULL,
  `exam_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqokej53frr32bhbn960347dy` (`exam_id`),
  KEY `FKdq2boly28t9o71dab63u2icqa` (`user_id`),
  CONSTRAINT `FKdq2boly28t9o71dab63u2icqa` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKqokej53frr32bhbn960347dy` FOREIGN KEY (`exam_id`) REFERENCES `Exam` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExamSession`
--

LOCK TABLES `ExamSession` WRITE;
/*!40000 ALTER TABLE `ExamSession` DISABLE KEYS */;
INSERT INTO `ExamSession` VALUES (1,1,1,2),(2,1,3,2),(3,2,2,2),(4,2,1,3),(5,2,3,3),(6,3,2,3),(7,1,1,4),(8,3,3,4),(9,2,2,4);
/*!40000 ALTER TABLE `ExamSession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Question`
--

DROP TABLE IF EXISTS `Question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `points` int NOT NULL,
  `question` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `exam_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcgdj3h2yuaw5ji6ehj20n09mk` (`exam_id`),
  CONSTRAINT `FKcgdj3h2yuaw5ji6ehj20n09mk` FOREIGN KEY (`exam_id`) REFERENCES `Exam` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Question`
--

LOCK TABLES `Question` WRITE;
/*!40000 ALTER TABLE `Question` DISABLE KEYS */;
INSERT INTO `Question` VALUES (1,1,'Capital of Australia?','Geography: capital cities',1),(2,1,'Capital of France?','Geography: capital cities',1),(3,1,'Capital of Germany?','Geography: capital cities',1),(4,1,'Capital of Italy?','Geography: capital cities',1),(5,1,'Capital of Canada?','Geography: capital cities',1),(6,1,'5 + 12 = ?','Math: sum of two numbers',2),(7,1,'62 + 14 = ?','Math: sum of two numbers',2),(8,1,'88 + 22 = ?','Math: sum of two numbers',2),(9,1,'143 + 18 = ?','Math: sum of two numbers',2),(10,1,'77 + 69 = ?','Math: sum of two numbers',2),(11,1,'What is the difference between a database and a data warehouse?','Information technologies',3),(12,1,'What is the purpose of a firewall in network security?','Information technologies',3),(13,1,'What is the most common way to deploy machine learning models in production?','Information technologies',3),(14,1,'What is the main benefit of cloud computing?','Information technologies',3),(15,1,'What is Agile software development?','Information technologies',3);
/*!40000 ALTER TABLE `Question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'Petras','Petraitis'),(2,'Arturas','Minderis'),(3,'Darius','Guginis'),(4,'Tomas','Ramanauskas');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-06 11:58:24
