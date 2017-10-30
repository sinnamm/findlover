-- MySQL dump 10.13  Distrib 5.7.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: findlover
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `admin`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `flag` int(11) NOT NULL DEFAULT '1' COMMENT '0：超级管理员、1：普通管理员',
  `create_time` datetime NOT NULL,
  `last_login` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--


--
-- Table structure for table `admin_role`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role` (
  `admin_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `FK_fk_araid` (`admin_id`),
  KEY `FK_fk_arrid` (`role_id`),
  CONSTRAINT `FK_fk_araid` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_fk_arrid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role`
--


--
-- Table structure for table `complain`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `com_obj` int(11) NOT NULL,
  `reason` varchar(50) NOT NULL,
  `content` varchar(50) DEFAULT NULL,
  `com_time` datetime NOT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0：待处理、1：忽略、2：警告、3：封号',
  `admin_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_comadminid` (`admin_id`),
  KEY `FK_fk_comobjid` (`com_obj`),
  KEY `FK_fk_comuserid` (`user_id`),
  CONSTRAINT `FK_fk_comadminid` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_fk_comobjid` FOREIGN KEY (`com_obj`) REFERENCES `user_basic` (`id`),
  CONSTRAINT `FK_fk_comuserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complain`
--


--
-- Table structure for table `dict`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

INSERT INTO `dict` VALUES (1,'education','高中及以下'),(2,'education','中专'),(3,'education','大专'),(4,'education','大学本科'),(5,'education','硕士'),(6,'education','博士'),(12,'marry_status','未婚'),(13,'marry_status','离异'),(14,'marry_status','丧偶'),(15,'job','销售'),(16,'job','客户服务'),(17,'job','计算机/互联网'),(18,'job','通信/电子'),(19,'job','生产/制造'),(20,'job','物流/仓储'),(21,'job','商贸/采购'),(22,'job','人事/行政'),(23,'job','高级管理'),(24,'job','广告/市场'),(25,'job','传媒/艺术'),(26,'job','生物/制药'),(27,'job','医疗/护理'),(28,'job','金融/保险'),(29,'job','建筑/房地产'),(30,'job','咨询/顾问'),(31,'job','法律'),(32,'job','财会/审计'),(33,'job','教育/科研'),(34,'job','服务业'),(35,'job','交通运输'),(36,'job','政府机构'),(37,'job','军人/警察'),(38,'job','农林牧渔'),(39,'job','自由职业'),(40,'job','在校学生'),(41,'job','待业'),(42,'job','其他行业'),(43,'animal','鼠'),(44,'animal','牛'),(45,'animal','虎'),(46,'animal','兔'),(47,'animal','龙'),(48,'animal','蛇'),(49,'animal','马'),(50,'animal','羊'),(51,'animal','猴'),(52,'animal','鸡'),(53,'animal','狗'),(54,'animal','猪'),(55,'zodiac','白羊座'),(56,'zodiac','金牛座'),(57,'zodiac','双子座'),(58,'zodiac','巨蟹座'),(59,'zodiac','狮子座'),(60,'zodiac','处女座'),(61,'zodiac','天秤座'),(62,'zodiac','天蝎座'),(63,'zodiac','射手座'),(64,'zodiac','摩羯座'),(65,'zodiac','水平座'),(66,'zodiac','双鱼座'),(67,'religion','不信教'),(68,'religion','佛教'),(69,'religion','道教'),(70,'religion','伊斯兰教'),(71,'religion','基督教'),(72,'religion','天主教'),(73,'religion','儒家门徒'),(74,'religion','不可知论者'),(75,'religion','其他宗教'),(76,'job_time','有双休'),(77,'job_time','工作忙碌'),(78,'job_time','工作清闲'),(79,'job_time','自由工作出差'),(80,'job_time','经常出差'),(81,'love_history','初恋还在'),(82,'love_history','谈过3次以内恋爱'),(83,'love_history','情场高手'),(84,'marry_time','认同闪婚'),(85,'marry_time','一年内'),(86,'marry_time','两年内'),(87,'marry_time','三年内'),(88,'marry_time','时机成熟就结婚'),(89,'parent_status','父母均健在'),(90,'parent_status','只有母亲健在'),(91,'parent_status','只有父亲健在'),(92,'parent_status','父母均已离世'),(93,'bro_and_sis','独生子女'),(94,'bro_and_sis','2'),(95,'bro_and_sis','3'),(96,'bro_and_sis','4'),(97,'bro_and_sis','更多'),(98,'com_reason','违法信息'),(99,'com_reason','有害信息'),(100,'com_reason','人身攻击我'),(101,'live_condition','和家人同住'),(102,'live_condition','已购房'),(103,'live_condition','租房'),(104,'live_condition','打算婚后购房'),(105,'live_condition','单位宿舍');

--
-- Table structure for table `essay`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `essay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `writer_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `filename` varchar(50) DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0：下架，1：审核通过，2：待审核',
  `admin_id` int(11) DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `visit_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_essayadminid` (`admin_id`),
  KEY `FK_fk_wewid` (`writer_id`),
  CONSTRAINT `FK_fk_essayadminid` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_fk_wewid` FOREIGN KEY (`writer_id`) REFERENCES `writer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `essay`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger Trigger_writer_essay after insert
on essay for each row
update writer set essay_count=essay_count+1 where id=new.writer_id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `follow`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `follow_id` int(11) DEFAULT NULL,
  `follow_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_uffollowid` (`follow_id`),
  KEY `FK_fk_ufuserid` (`user_id`),
  CONSTRAINT `FK_fk_uffollowid` FOREIGN KEY (`follow_id`) REFERENCES `user_basic` (`id`),
  CONSTRAINT `FK_fk_ufuserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--


--
-- Table structure for table `label`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `meaning` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label`
--

INSERT INTO `label` VALUES (1,'高收入','high_salary'),(2,'高学历','high_education'),(3,'有车一族','have_car'),(4,'有房一族','have_house'),(5,'公务员','civil_servant'),(6,'程序员','programmer');

--
-- Table structure for table `letter`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `letter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_id` int(11) DEFAULT NULL,
  `recieve_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_letterrecieveid` (`recieve_id`),
  KEY `FK_fk_lettersendid` (`send_id`),
  CONSTRAINT `FK_fk_letterrecieveid` FOREIGN KEY (`recieve_id`) REFERENCES `user_basic` (`id`),
  CONSTRAINT `FK_fk_lettersendid` FOREIGN KEY (`send_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100037 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `letter`
--

INSERT INTO `letter` VALUES (100001,100001,100005,'hello','2017-10-21 17:35:56',1),(100002,100005,100001,'hello','2017-10-21 17:36:19',1),(100003,100005,100001,'hello','2017-10-21 17:36:37',1),(100004,100001,100005,'hello','2017-10-21 17:37:01',1),(100005,100005,100001,'hello','2017-10-21 17:37:26',1),(100006,100002,100005,'hello','2017-10-23 10:18:50',1),(100007,100003,100002,'hello','2017-10-23 10:19:11',0),(100008,100002,100003,'hahahahaha','2017-10-23 10:19:43',1),(100009,100005,100003,'hahahahaha','2017-10-23 10:19:53',1),(100010,100003,100005,'nihao','2017-10-23 10:20:28',1),(100011,100005,100003,'hahahahaha','2017-10-23 10:20:41',1),(100012,100005,100007,'hahahahaha','2017-10-23 10:20:54',1),(100013,100007,100005,'nihao','2017-10-23 10:21:14',1),(100014,100007,100005,'hahahahaha','2017-10-23 10:21:36',1),(100015,100005,100006,'hello','2017-10-23 10:22:22',1),(100016,100006,100005,'hahahahaha','2017-10-23 10:22:39',1),(100017,100005,100006,'nihao','2017-10-23 10:22:59',1),(100018,100002,100003,'hahahahaha','2017-10-24 15:59:08',0),(100019,100002,100003,'nihao','2017-10-24 15:59:22',0),(100020,100003,100002,'nihao','2017-10-24 15:59:45',0),(100021,100002,100003,'hahahahaha','2017-10-24 16:00:02',0),(100022,100005,100001,'hahahahaha','2017-10-24 19:48:46',1),(100023,100001,100005,'nihao','2017-10-24 19:48:48',1),(100024,100005,100001,'hahahahaha','2017-10-24 19:48:49',1),(100025,100002,100005,'hahahahaha','2017-10-24 19:48:51',1),(100026,100003,100002,'nihao','2017-10-24 19:48:53',0),(100027,100002,100003,'hahahahaha','2017-10-24 19:48:54',0),(100028,100005,100003,'hello','2017-10-24 19:48:55',1),(100029,100003,100005,'hahahahaha','2017-10-24 19:48:56',1),(100030,100005,100003,'nihao','2017-10-24 19:48:57',1),(100031,100005,100007,'hahahahaha','2017-10-24 19:48:57',1),(100032,100007,100005,'hello','2017-10-24 19:48:58',1),(100033,100007,100005,'hello','2017-10-24 19:48:59',1),(100034,100005,100006,'hello','2017-10-24 19:49:05',1),(100035,100002,100003,'nihao','2017-10-24 19:50:17',0),(100036,100003,100002,'nihao','2017-10-24 19:50:24',0);

--
-- Table structure for table `message`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `reply_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_msguserid` (`user_id`),
  CONSTRAINT `FK_fk_msguserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

INSERT INTO `message` VALUES (2,100006,'Gss沙发!','2017-10-17 16:55:50',0,0),(3,100006,'Gss-板凳！','2017-10-17 17:02:03',0,0),(4,100006,'Gss-马扎','2017-10-17 17:03:44',0,0);

--
-- Table structure for table `message_like`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `like_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_msgrlikemid` (`message_id`),
  KEY `FK_fk_msgruid` (`user_id`),
  CONSTRAINT `FK_fk_msgrlikemid` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_fk_msgruid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_like`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger Trigger_msgreplylike after insert
on message_like for each row
update message set like_count=like_count+1 where id=new.message_id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `message_reply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_mrmsgid` (`message_id`),
  KEY `FK_fk_msgruserid` (`user_id`),
  CONSTRAINT `FK_fk_mrmsgid` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_fk_msgruserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_reply`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger Trigger_msg_reply after insert
on message_reply for each row
update message set reply_count=reply_count+1 where id=new.message_id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `notice`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL,
  `pub_obj` int(11) DEFAULT NULL COMMENT '0:所有用户，1:vip，2:星级用户，Id:用户id',
  PRIMARY KEY (`id`),
  KEY `FK_fk_noticeaaid` (`admin_id`),
  CONSTRAINT `FK_fk_noticeaaid` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--


--
-- Table structure for table `notice_user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice_user` (
  `notice_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `read_time` datetime NOT NULL,
  PRIMARY KEY (`notice_id`),
  KEY `FK_fk_unuid` (`user_id`),
  CONSTRAINT `FK_fk_unnid` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`id`),
  CONSTRAINT `FK_fk_unuid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice_user`
--


--
-- Table structure for table `permission`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `value` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--


--
-- Table structure for table `role`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `note` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--


--
-- Table structure for table `role_permission`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  KEY `FK_fk_rmmid` (`permission_id`),
  KEY `FK_fk_rmrid` (`role_id`),
  CONSTRAINT `FK_fk_rmmid` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FK_fk_rmrid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--


--
-- Table structure for table `success_story`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `success_story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `left_user` int(11) DEFAULT NULL,
  `right_user` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `success_time` datetime DEFAULT NULL,
  `photo` varchar(50) DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `reply_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0：下架，1：审核通过，2：待右手审核，3：待管理员审核',
  `admin_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_ssadminid` (`admin_id`),
  KEY `FK_fk_ucleftid` (`left_user`),
  KEY `FK_fk_ucrightid` (`right_user`),
  CONSTRAINT `FK_fk_ssadminid` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_fk_ucleftid` FOREIGN KEY (`left_user`) REFERENCES `user_basic` (`id`),
  CONSTRAINT `FK_fk_ucrightid` FOREIGN KEY (`right_user`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `success_story`
--

INSERT INTO `success_story` VALUES (1,100000,100003,'我们的恋爱历程','\n          <h3 style=\"margin: 30px\">我们的恋爱历程</h3>\n\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因为身边有朋友在珍爱网服务过，有成功找到对象的，也有没找到的，不过这种个人因素占很大比重的事情，苛求百分百的成功率也不太现实。所以我觉得这还是不错的方式，试试也不错，就去珍爱网深圳地王店线下服务中心加入了会员，想多给自己一些机会。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我还是有挺多顾虑的，害怕碰到熟人，多少有点尴尬，也怕遇到渣男，被欺骗感情。不过事实证明是我多虑了，他还挺好的，第一次见面就觉得无论是着装还是谈吐都很吸引人，相处了快一年，日久见人心，他是个品质优秀也对我很好的人。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从认识到现在，每次节日，不论东方的西方的，成文的不成文的，除了礼物每次都会送一束玫瑰花，不知道别的女孩子是什么样的，我是很爱花的，觉得谈恋爱一定要有玫瑰的香味氤氲才称得上浪漫。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他送过我很多小礼物，都是生活中用得上的，很贴心也很用心的一个人，会送手机啊，还有一些保健品，其他的还有太多都想不起来了。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在一起不久我们就出国去旅游了一次，当时刚好碰上都有假期，就来了一场说走就走的旅行，感情里充满了惊喜。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现在感情很稳定，每周都会见个两三次，每天晚上回家了还都会视频聊天，也都很走心，虽然年纪不大，但是奔着结婚去的，都见过了家长。偶尔也会拌拌嘴，不过一般都是我在闹，他在笑。从来没有什么原则性的事情会争吵，都是女生的小情绪，发点小脾气，哄一哄就好了。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除了跟男朋友要磨合，跟红娘之间其实也是有一段时间的磨合期的，真正信任了红娘老师，会对自己的征婚有很大的帮助。我前前后后也见过了几个会员，综合了解之后，发现很多条件并不是像自己制定的那么苛刻，而且红娘老师可能不是在某一个人身上教给了你一些东西，而是在你觉得不满意的时候，帮忙分析原因，这对以后的感情生活也很有帮助，因为两个人不合适往往不是一个人的事情。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们俩年纪都不大，想要再谈几年，双方家庭都比较传统，结了婚就会被催着要小孩，会有各种约束，现在的恋爱状态彼此都很自由很享受，还暂时不想改变。等时间到了，也就会结婚了。</p>\n          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;很感恩珍爱网，愿意分享自己的故事也是基于对珍爱网的信任和感谢，人海茫茫遇到一个合适的人不容易，很多人蹉跎中错过了很多，我庆幸自己迈出了这一步。同时也很感谢红娘朱老师，教给了我很多东西，甚至拿自己跟先生的一些事例来告诉我爱情里和婚姻里的处事之道，真的很贴心。</p>\n      ','2017-10-17 15:15:53','wed.jpg',1,1,1,NULL);

--
-- Table structure for table `success_story_like`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `success_story_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `success_story_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `like_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_sslikesid` (`success_story_id`),
  KEY `FK_fk_sslikeuid` (`user_id`),
  CONSTRAINT `FK_fk_sslikesid` FOREIGN KEY (`success_story_id`) REFERENCES `success_story` (`id`),
  CONSTRAINT `FK_fk_sslikeuid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `success_story_like`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger trigger_sslike after insert
on success_story_like for each row
update success_story set like_count=like_count+1 where id=new.success_story_id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `success_story_reply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `success_story_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ss_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `reply_id` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_ssrssid` (`ss_id`),
  KEY `FK_fk_ssruserid` (`user_id`),
  CONSTRAINT `FK_fk_ssrssid` FOREIGN KEY (`ss_id`) REFERENCES `success_story` (`id`),
  CONSTRAINT `FK_fk_ssruserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `success_story_reply`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger trigger_ssreply after insert
on success_story_reply for each row
update success_story set reply_count=reply_count+1 where id=new.ss_id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user_asset`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_asset` (
  `id` int(11) NOT NULL,
  `vip_deadline` datetime DEFAULT '1970-01-01 11:11:11',
  `star_deadline` datetime DEFAULT '1970-01-01 11:11:11',
  `asset` int(11) DEFAULT '0',
  `cost` double DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_fk_uaid` FOREIGN KEY (`id`) REFERENCES `user_basic` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_asset`
--

INSERT INTO `user_asset` VALUES (100000,'2017-12-10 16:55:21','2017-12-29 16:56:16',100,0),(100001,'2017-11-23 19:06:29','2015-10-23 21:19:37',0,52.1),(100002,'2017-12-10 16:55:21','2017-11-17 16:55:30',0,0),(100003,'2017-10-20 16:56:00','2017-12-09 20:21:23',0,0),(100005,'2020-06-29 16:55:21','2018-01-04 16:56:35',948,3699.24),(100008,'2018-10-23 18:19:21','2017-11-02 18:19:14',0,521),(100009,'2018-10-23 21:09:51','2017-11-04 21:09:07',0,625.2),(100011,'2018-10-23 21:31:46','2017-11-09 21:31:43',0,885.7),(100012,'2017-11-24 14:00:52','2017-11-20 14:00:48',0,1406.7);

--
-- Table structure for table `user_basic`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_basic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `tel` varchar(50) NOT NULL,
  `sex` char(2) NOT NULL,
  `birthday` date NOT NULL,
  `photo` varchar(50) NOT NULL,
  `marry_status` varchar(50) NOT NULL,
  `height` int(11) NOT NULL,
  `sexual` char(2) NOT NULL,
  `education` varchar(50) NOT NULL,
  `workplace` varchar(50) NOT NULL,
  `salary` double(9,2) NOT NULL,
  `live_condition` varchar(50) DEFAULT NULL,
  `authority` int(11) NOT NULL DEFAULT '1' COMMENT '个人资料可见性（0：所有用户不可见，1：所有用户可见，2：仅我关注的人可见）',
  `status` int(11) NOT NULL COMMENT '账户状态（0：锁定，1：激活，2：未激活）',
  `code` varchar(255) DEFAULT NULL COMMENT '用户激活码',
  `reg_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100046 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_basic`
--

INSERT INTO `user_basic` VALUES (100000,'gss@qq.com','202CB962AC59075B964B07152D234B70','gsssss','123','男','1997-07-19','p7.jpg','未婚',175,'男','大学本科','山东-济南',8000.00,'单位宿舍',1,1,NULL,'2017-10-17 21:02:53'),(100001,'a@a.com','202CB962AC59075B964B07152D234B70','Tom','13222131223','男','1997-10-16','p7.jpg','未婚',173,'女','博士','山东-济宁',20000.00,'已购房',1,1,NULL,'2017-10-17 15:12:13'),(100002,'sinna@163.com','202CB962AC59075B964B07152D234B70','sinna','18764543221','女','1996-08-01','p6.jpg','未婚',168,'男','大学本科','北京-朝阳区',5000.00,'已购房',1,1,NULL,'2017-10-17 15:12:09'),(100003,'sinnamm@163.com','202CB962AC59075B964B07152D234B70','sinnamm','123','女','1996-08-01','p6.jpg','未婚',168,'女','大学本科','北京-海淀区',7000.00,'打算婚后购房',1,1,NULL,'2017-10-17 15:12:09'),(100004,'1472610316@qq.com','202CB962AC59075B964B07152D234B70','aaa','17865166639','男','1987-10-06','p6.jpg','未婚',161,'男','大专','山东-淄博',11111.00,'已购房',2,1,'50005637-14b3-4599-8416-b1a86639dca8','2017-10-18 11:48:14'),(100005,'b@a.com','202CB962AC59075B964B07152D234B70','昵称请看个性签名','13222222222','男','1994-10-06','p6.jpg','未婚',164,'女','高中及以下','山东-济宁',3000.00,'和家人同住',0,1,'b96086a9-f706-41f0-9a3a-33fc167fb7d8','2017-10-17 18:05:29'),(100006,'gss@gss.com','202CB962AC59075B964B07152D234B70','Gss','132','男','1991-10-16','p7.jpg','未婚',173,'女','大学本科','山东-菏泽',5000.00,'单位宿舍',1,1,NULL,'2017-10-17 15:12:13'),(100007,'zhangsan@163.com','202CB962AC59075B964B07152D234B70','zhangsan','16527783355','男','1992-12-01','p6.jpg','未婚',177,'女','硕士','山东-青岛',8000.00,'和家人同住',1,1,'a82b2b1f-ee86-43d3-a5d4-67327b91a063','2017-10-21 15:29:56'),(100008,'caocao1@163.com','202CB962AC59075B964B07152D234B70','曹操1','13888888888','男','1999-05-15','p6.jpg','离异',207,'男','大学本科','北京-崇文区',8000.00,'单位宿舍',1,1,'e3e85ec7-01e8-4370-885f-42f8b7b7d684','2017-10-23 18:00:30'),(100009,'caocao2@163.com','202CB962AC59075B964B07152D234B70','曹操2','13888888888','女','1991-07-10','p6.jpg','未婚',164,'女','大专','山东-东营',15000.00,'打算婚后购房',1,1,'5be6c299-90fc-40b9-8ed1-e14cfc9c3e3b','2017-10-23 20:51:54'),(100010,'caocao3@163.com','202CB962AC59075B964B07152D234B70','曹操3','13888888888','男','1994-02-10','p6.jpg','未婚',205,'男','大学本科','重庆-渝北区',20000.00,'单位宿舍',1,1,'12d8e7c4-6b2a-4e16-b3fe-aca4bb9d53dc','2017-10-23 21:15:38'),(100011,'caocao4@163.com','202CB962AC59075B964B07152D234B70','曹操4','13888888888','男','1994-01-11','p6.jpg','未婚',210,'女','高中及以下','山东-济南',12000.00,'已购房',1,1,'eb472615-475e-4236-8d5e-926287b17703','2017-10-23 21:31:21'),(100012,'caocao5@163.com','202CB962AC59075B964B07152D234B70','曹操5','13454534533','男','1991-02-22','p6.jpg','未婚',172,'女','大学本科','山东-威海',8000.00,'和家人同住',1,1,'9d8a5a04-68db-46cb-aa27-40ba5e760982','2017-10-24 11:13:32'),(100015,'zhangsan0@qq.com','202CB962AC59075B964B07152D234B70','张三0','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100016,'zhangsan1@qq.com','202CB962AC59075B964B07152D234B70','张三1','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100017,'zhangsan2@qq.com','202CB962AC59075B964B07152D234B70','张三2','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100018,'zhangsan3@qq.com','202CB962AC59075B964B07152D234B70','张三3','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100019,'zhangsan4@qq.com','202CB962AC59075B964B07152D234B70','张三4','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100020,'zhangsan5@qq.com','202CB962AC59075B964B07152D234B70','张三5','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100021,'zhangsan6@qq.com','202CB962AC59075B964B07152D234B70','张三6','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100022,'zhangsan7@qq.com','202CB962AC59075B964B07152D234B70','张三7','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100023,'zhangsan8@qq.com','202CB962AC59075B964B07152D234B70','张三8','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100024,'zhangsan9@qq.com','202CB962AC59075B964B07152D234B70','张三9','123214421421','男','1996-01-01','no','未婚',175,'女','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100025,'lisi0@qq.com','202CB962AC59075B964B07152D234B70','李四0','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100026,'lisi1@qq.com','75B964B07152D234B70','李四1','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100027,'lisi2@qq.com','123','李四2','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100028,'lisi3@qq.com','123','李四3','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100029,'lisi4@qq.com','123','李四4','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100030,'lisi5@qq.com','123','李四5','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100031,'lisi6@qq.com','123','李四6','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100032,'lisi7@qq.com','123','李四7','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100033,'lisi8@qq.com','123','李四8','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100034,'lisi9@qq.com','123','李四9','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100035,'ruhua0@qq.com','75B964B07152D234B70','如花0','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100036,'ruhua1@qq.com','75B964B07152D234B70','如花1','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100037,'ruhua2@qq.com','75B964B07152D234B70','如花2','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100038,'ruhua3@qq.com','75B964B07152D234B70','如花3','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100039,'ruhua4@qq.com','75B964B07152D234B70','如花4','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100040,'ruhua5@qq.com','75B964B07152D234B70','如花5','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100041,'ruhua6@qq.com','75B964B07152D234B70','如花6','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100042,'ruhua7@qq.com','75B964B07152D234B70','如花7','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100043,'ruhua8@qq.com','75B964B07152D234B70','如花8','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100044,'ruhua9@qq.com','75B964B07152D234B70','如花9','123214421421','女','1996-01-01','no','未婚',175,'男','大学本科','山东-济南',6000.00,NULL,1,1,NULL,'2017-01-10 00:24:00'),(100045,'g@g.com','202CB962AC59075B964B07152D234B70','奔跑的小猪199','15555555555','男','1991-01-01','p6.jpg','未婚',179,'男','中专','内蒙古-锡林郭勒盟',5000.00,'和家人同住',1,1,'95a4d443-cb80-4636-bf90-afbcb2fed269','2017-10-24 22:32:29');
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger trigger_seletterxual after update
on user_basic for each row
update user_pick set sex=new.sexual where id=new.id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_detail` (
  `id` int(11) NOT NULL,
  `realname` varchar(50) DEFAULT NULL,
  `cardnumber` varchar(50) DEFAULT NULL,
  `birthplace` varchar(50) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `animal` char(2) DEFAULT NULL,
  `zodiac` char(6) DEFAULT NULL,
  `nation` varchar(20) DEFAULT NULL,
  `religion` varchar(20) DEFAULT NULL,
  `graduation` varchar(50) DEFAULT NULL,
  `hobby` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_fk_udid` FOREIGN KEY (`id`) REFERENCES `user_basic` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_detail`
--

INSERT INTO `user_detail` VALUES (100000,'lisi','123214421','山东-菏泽',NULL,NULL,NULL,NULL,NULL,NULL,'李四李四李四李四','李四李四李四李四'),(100001,'帅帅','4454151515','山东-济宁',67,'虎','天蝎座','汉族','佛教','山东-青岛科技大学','爱睡觉，爱打撸啊撸，爱跑步','生命诚可贵，爱情价更高'),(100002,'gggg',NULL,'山东-青岛',12,NULL,NULL,NULL,NULL,'山东',NULL,'我不知道该说些什么，反正项目好难写啊啊啊'),(100003,'王五','221321321','山东-济宁',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'王五王五王五那我 昂科威蝴蝶卡'),(100004,'赵六','3242314','山东-济宁',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'胜多负少看到回复规划书快递费 '),(100007,'张三','452127199903142713','安徽-亳州',66,'虎','双子座','蒙古族','佛教','广东-广州大学','张三张三张三','张三张三张三张三'),(100008,NULL,NULL,'山东-菏泽',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'不忘初心，牢记使命，高举中国特色社会主义伟大旗帜'),(100009,NULL,NULL,'山东-临沂',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'万众瞩目的十九大已于今天拉开帷幕，今天上午习大大长达 3 个小时\r\n\r\n'),(100011,NULL,NULL,'山东-淄博',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'4.思想文化建设取得重大进展。国家文化软实力和中华文化影响力大幅提升'),(100012,'我叫曹操','452127199903142713','山东-临沂',NULL,NULL,NULL,NULL,NULL,'北京-北京交通大学',NULL,'3.意味着中国特色社会主义道路、理论、制度、文化不断发展');

--
-- Table structure for table `user_label`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_label` (
  `user_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  KEY `FK_fk_ullabelid` (`label_id`),
  KEY `FK_fk_uluserid` (`user_id`),
  CONSTRAINT `FK_fk_ullabelid` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`),
  CONSTRAINT `FK_fk_uluserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_label`
--

INSERT INTO `user_label` VALUES (100000,1),(100004,1),(100007,1),(100001,1),(100001,2),(100008,1),(100008,2),(100008,3),(100008,5),(100008,4),(100011,5),(100012,3),(100012,5),(100012,1),(100012,2),(100002,2),(100002,4);

--
-- Table structure for table `user_life`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_life` (
  `id` int(11) NOT NULL,
  `smoke` int(11),
  `drink` int(11),
  `car` int(11),
  `job` varchar(50) DEFAULT NULL,
  `job_time` varchar(50) DEFAULT NULL,
  `character` varchar(255) DEFAULT NULL,
  `job_brief` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_fk_ulid` FOREIGN KEY (`id`) REFERENCES `user_basic` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_life`
--

INSERT INTO `user_life` VALUES (100000,NULL,NULL,NULL,'通信/电子',NULL,NULL,NULL),(100001,0,1,0,'服务业','工作忙碌','阳光开朗','轻松愉快'),(100003,NULL,NULL,NULL,'通信/电子',NULL,NULL,NULL),(100004,NULL,NULL,NULL,'通信/电子',NULL,NULL,NULL),(100005,NULL,NULL,NULL,'政府机构',NULL,NULL,NULL),(100007,0,1,1,'物流/仓储','工作清闲','张三张三张三张三张三','张三张三张三张三张三张三'),(100008,NULL,NULL,1,'政府机构',NULL,NULL,NULL),(100011,NULL,NULL,0,'政府机构',NULL,NULL,NULL),(100012,NULL,NULL,1,'政府机构',NULL,NULL,NULL),(100045,1,1,0,'教育/科研',NULL,NULL,NULL);

--
-- Table structure for table `user_login_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `login_ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_uluuid` (`user_id`),
  CONSTRAINT `FK_fk_uluuid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login_log`
--


--
-- Table structure for table `user_photo`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_ufid` (`user_id`),
  CONSTRAINT `FK_fk_ufid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_photo`
--

INSERT INTO `user_photo` VALUES (1,100012,'group1/M00/00/00/rBEutlnu_VOANWNzAAIgNjKzmg4771.png'),(2,100012,'group1/M00/00/00/rBEutlnwR3yAXSsPAABtmAicq_w796.png');

--
-- Table structure for table `user_pick`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_pick` (
  `id` int(11) NOT NULL,
  `sex` char(2) NOT NULL COMMENT '根据性取向生成，用户不可修改',
  `age_low` int(11) NOT NULL,
  `age_high` int(11) NOT NULL,
  `workplace` varchar(50) NOT NULL,
  `birthplace` varchar(50) DEFAULT NULL,
  `marry_status` varchar(50) DEFAULT NULL,
  `education` varchar(50) DEFAULT NULL,
  `salary_low` double DEFAULT NULL,
  `salary_high` double DEFAULT NULL,
  `height_low` int(11) DEFAULT NULL,
  `height_high` int(11) DEFAULT NULL,
  `job` varchar(50) DEFAULT NULL,
  `drink` int(11),
  `smoke` int(11),
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_fk_upid` FOREIGN KEY (`id`) REFERENCES `user_basic` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_pick`
--

INSERT INTO `user_pick` VALUES (100001,'女',18,33,'山东-青岛','山东-菏泽','未婚','硕士',3000,5000,185,195,'客户服务',0,0),(100002,'男',20,25,'山东-济南',NULL,'未婚','大学本科',3000,20000,NULL,NULL,NULL,NULL,NULL),(100004,'男',20,30,'山东-济南',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(100007,'女',18,33,'河南-许昌','山东-菏泽','离异','大专',3000,8000,167,187,'通信/电子',0,NULL),(100008,'男',18,33,'山东','山东-菏泽','未婚','大学本科',8000,15000,197,188,'政府机构',0,1),(100009,'女',23,29,'山东-青岛',NULL,'未婚',NULL,12000,20000,154,174,'计算机/互联网',1,1),(100010,'男',20,26,'重庆-渝北区',NULL,'未婚',NULL,NULL,NULL,195,215,NULL,NULL,NULL),(100011,'女',20,26,'山东',NULL,'未婚','大专',5000,15000,200,210,NULL,NULL,NULL),(100012,'女',23,29,'山东-威海',NULL,'未婚',NULL,NULL,NULL,162,182,NULL,NULL,NULL),(100015,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100016,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100017,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100018,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100019,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100020,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100021,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100022,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100023,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100024,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100025,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100026,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100027,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100028,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100029,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100030,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100031,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100032,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100033,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100034,'男',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100035,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100036,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100037,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100038,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100039,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100040,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100041,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100042,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100043,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100044,'女',18,30,'山东济南',NULL,'未婚',NULL,NULL,NULL,160,180,NULL,NULL,NULL),(100045,'男',23,29,'内蒙古-锡林郭勒盟',NULL,'未婚',NULL,NULL,NULL,169,189,NULL,NULL,NULL);

--
-- Table structure for table `user_status`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_status` (
  `id` int(11) NOT NULL,
  `love_history` varchar(50) DEFAULT NULL,
  `marry_time` varchar(50) DEFAULT NULL,
  `ldr` int(11) DEFAULT NULL,
  `parent_status` varchar(50) DEFAULT NULL,
  `bro_and_sis` varchar(50) DEFAULT NULL,
  `family_brief` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_fk_usid` FOREIGN KEY (`id`) REFERENCES `user_basic` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

INSERT INTO `user_status` VALUES (100001,'情场高手','三年内',0,'父母均健在','2','家庭美满幸福'),(100007,'初恋还在','一年内',0,'只有母亲健在','4','张三张三张三张三张三张三张三');

--
-- Table structure for table `visit_trace`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit_trace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `interviewee_id` int(11) NOT NULL,
  `visit_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_uvuserid` (`user_id`),
  KEY `FK_fk_uvvid` (`interviewee_id`),
  CONSTRAINT `FK_fk_uvuserid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`),
  CONSTRAINT `FK_fk_uvvid` FOREIGN KEY (`interviewee_id`) REFERENCES `user_basic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_trace`
--


--
-- Table structure for table `writer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `writer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pseudonym` varchar(50) DEFAULT NULL,
  `reg_time` datetime DEFAULT NULL,
  `essay_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `writer`
--


--
-- Table structure for table `writer_essay_like`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `writer_essay_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `essay_id` int(11) DEFAULT NULL,
  `like_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_essay_like_userid` (`user_id`),
  KEY `FK_fk_welikeeid` (`essay_id`),
  CONSTRAINT `FK_fk_essay_like_userid` FOREIGN KEY (`user_id`) REFERENCES `user_basic` (`id`),
  CONSTRAINT `FK_fk_welikeeid` FOREIGN KEY (`essay_id`) REFERENCES `essay` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `writer_essay_like`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger Trigger_essay_like after insert
on writer_essay_like for each row
update essay set like_count=like_count+1 where id=new.essay_id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-25 17:42:48
